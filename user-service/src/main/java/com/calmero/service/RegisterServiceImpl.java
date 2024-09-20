package com.calmero.service;

import com.calmero.config.UserLogger;
import com.calmero.exception.CustomException;
import com.calmero.interfaces.repository.UserRepository;
import com.calmero.interfaces.service.RegisterService;
import com.calmero.kafka.producer.KafkaProducer;
import com.calmero.model.Role;
import com.calmero.model.entity.UserEntity;
import com.calmero.model.request.RegisterContextRequest;
import com.calmero.model.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.calmero.constant.ErrorConstant.ErrorMessage.USERNAME_IN_USE;
import static com.calmero.constant.SuccessConstant.FAILED;
import static com.calmero.constant.SuccessConstant.SUCCESS;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserLogger LOG;
    private final KafkaProducer<UserEntity, Mono<Void>> kafkaUserRegisterEventProducer;

    @Override
    @Transactional
    public Mono<String> register(Mono<RegisterRequest> registerRequestMono) {
        var traceId = ThreadContext.get("traceId");
        var requestContext = registerRequestMono
                .doOnNext(body -> {
                    LOG.register.info(String.format("User register request with payload: %s", body.toString()));
                })
                .map(RegisterContextRequest::new);

        var userExists = requestContext
                .flatMap(registerContextRequest -> userRepository
                        .existsByUsername(registerContextRequest.getRegisterRequest().getUsername())
                        .doOnNext(registerContextRequest::setExistsByUsername)
                        .thenReturn(registerContextRequest))
                .filter(registerContextRequest -> !registerContextRequest.getExistsByUsername())
                .switchIfEmpty(Mono.error(new CustomException(USERNAME_IN_USE, HttpStatus.BAD_REQUEST)));

        var savedUser = userExists.flatMap(registerContextRequest -> this.userRepository.save(UserEntity.builder()
                                .username(registerContextRequest.getRegisterRequest().getUsername())
                                .name(registerContextRequest.getRegisterRequest().getName())
                                .password(passwordEncoder.encode(registerContextRequest.getRegisterRequest().getPassword()))
                                .role(Role.USER)
                                .build())
                        .map(registerContextRequest::setNewUser)
                        .thenReturn(registerContextRequest))
                .map(RegisterContextRequest::getNewUser);

        var cachedUser = savedUser.flatMap(newUser -> this.kafkaUserRegisterEventProducer.send(traceId, newUser)
                        .thenReturn(newUser)).map(senderResult -> SUCCESS)
                .onErrorResume(Mono::error)
                .doOnError(ex ->
                        LOG.register.info("User register failed with ex: {}", ex.getMessage(), ex)
                );

        var lastCheck = cachedUser.switchIfEmpty(Mono.just(FAILED))
                .doOnSuccess(s -> LOG.register.info("User register done with message: {}", s));
        return lastCheck.subscribeOn(Schedulers.boundedElastic());
    }
}

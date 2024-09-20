package com.calmero.controller;

import com.calmero.interfaces.controller.UserController;
import com.calmero.interfaces.service.UserService;
import com.calmero.model.view.UserView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public Mono<UserView> getUser() {
        return this.userService.getUser();
    }

    @Override
    public Flux<UserView> getUsers() {
        log.info("getUsers");
        return this.userService.getUsers();
    }
}

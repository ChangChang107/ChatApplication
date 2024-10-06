package com.calmero.consumer;

import com.calmero.interfaces.service.ChatSocketService;
import com.calmero.interfaces.service.SessionService;
import com.calmero.model.DispatchMessage;
import com.calmero.model.request.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static com.calmero.constant.PrefixConstant.*;
import static java.util.concurrent.CompletableFuture.runAsync;

@Component
@RequiredArgsConstructor
public class MessageConsumer implements RMessageConsumer<DispatchMessage<?>, Void> {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SessionService sessionService;
    private final ChatSocketService chatSocketService;

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public Void consume(DispatchMessage<?> dispatchMessage) throws RuntimeException {
        switch (dispatchMessage.getMessageType()) {
            case SEEN -> {
                SeenRequest seenRequest = (SeenRequest) dispatchMessage.getPayload();
                checkIsUserConnected(seenRequest, connected -> {
                    if (connected) {
                        runAsync(() -> simpMessagingTemplate.convertAndSend(String.format("%s%s/%s", MESSAGE_SEEN_DESTINATION_PREFIX, seenRequest.getChatId(), seenRequest.getMessageId()), true));
                        runAsync(() -> chatSocketService.seenMessageOperations(seenRequest));
                    }
                });
            }
            case ONLINE -> {
                OnlineRequest onlineRequest = (OnlineRequest) dispatchMessage.getPayload();
                //TODO implement here
            }
            case TYPING -> {
                TypingRequest typingRequest = (TypingRequest) dispatchMessage.getPayload();
                checkIsUserConnected(typingRequest, connected -> {
                    if (connected) {
                        simpMessagingTemplate.convertAndSend(String.format("%s%s", CHAT_TYPING_DESTINATION_PREFIX, typingRequest.getChatId()), true);
                    }
                });
            }
            case MESSAGE -> {
                MessageRequest messageRequest = (MessageRequest) dispatchMessage.getPayload();
                checkIsUserConnected(messageRequest, connected -> {
                    if (connected) {
                        runAsync(() -> simpMessagingTemplate.convertAndSend(MESSAGE_DESTINATION_PREFIX + messageRequest.getChatId(), messageRequest.getMessage()));
                        runAsync(() -> chatSocketService.saveMessageOperations(messageRequest));
                    }
                });
            }
        }
        return null;
    }

    private <T extends ParentMessageRequest> void checkIsUserConnected(T t, java.util.function.Consumer<Boolean> consumer) {
        consumer.accept(sessionService.isUserConnectedGlobally(t.getRecipientId()) && chatSocketService.isUserConnected(t.getRecipientId()));
    }
}

package com.calmero.interfaces.controller;

import com.calmero.interfaces.service.ChatSocketService;
import com.calmero.model.request.MessageRequest;
import com.calmero.model.request.OnlineRequest;
import com.calmero.model.request.SeenRequest;
import com.calmero.model.request.TypingRequest;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;

import javax.validation.Valid;

public interface ChatSocketController extends ParentController<ChatSocketService> {
    @MessageMapping("/chat/message/send")
    void sendMessage(@Valid @Payload MessageRequest messageRequest);

    @MessageMapping("/chat/message/seen")
    void seenMessage(@Valid @Payload SeenRequest seenRequest);

    @MessageMapping("/chat/typing")
    void typing(@Valid @Payload TypingRequest typingRequest);

    @MessageMapping("/chat/online")
    void online(@Valid @Payload OnlineRequest onlineRequest);

    @MessageExceptionHandler
    String handleException(Throwable exception);
}

package com.calmero.controller;

import com.calmero.interfaces.controller.ChatSocketController;
import com.calmero.interfaces.service.ChatSocketService;
import com.calmero.model.request.MessageRequest;
import com.calmero.model.request.OnlineRequest;
import com.calmero.model.request.SeenRequest;
import com.calmero.model.request.TypingRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatSocketControllerImpl implements ChatSocketController {
    private final ChatSocketService chatSocketService;

    @Override
    public ChatSocketService getService() {
        return chatSocketService;
    }

    @Override
    public void sendMessage(MessageRequest messageRequest) {
        getService().sendMessage(messageRequest);
    }

    @Override
    public void seenMessage(SeenRequest seenRequest) {
        getService().seenMessage(seenRequest);
    }

    @Override
    public void typing(TypingRequest typingRequest) {
        getService().typing(typingRequest);
    }

    @Override
    public void online(OnlineRequest onlineRequest) {
        getService().online(onlineRequest);
    }

    @Override
    public String handleException(Throwable exception) {
        LoggerFactory.getLogger(this.getClass()).info("Chat Exception : " + exception.getMessage());
        return exception.getMessage();
    }
}

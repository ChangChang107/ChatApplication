package com.calmero.interfaces.service;

import com.calmero.model.request.*;

public interface ChatSocketService {
    boolean isUserConnected(String name);

    void sendMessage(MessageRequest messageRequest);

    void saveMessageOperations(MessageRequest messageRequest);

    void seenMessage(SeenRequest seenRequest);

    void seenMessageOperations(SeenRequest seenRequest);

    void typing(TypingRequest typingRequest);

    void online(OnlineRequest onlineRequest);

    void deliverMessageOperations(DeliverRequest deliverRequest);
}

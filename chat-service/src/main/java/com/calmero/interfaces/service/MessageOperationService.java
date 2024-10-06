package com.calmero.interfaces.service;

public interface MessageOperationService {
    void labelDeliverMessages(String chatId, String receiverId);

    void labelSeenMessages(String chatId, String receiverId);
}

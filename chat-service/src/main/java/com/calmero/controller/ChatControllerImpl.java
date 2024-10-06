package com.calmero.controller;

import com.calmero.interfaces.controller.ChatController;
import com.calmero.interfaces.service.ChatService;
import com.calmero.model.request.CreateChatRoomRequest;
import com.calmero.model.request.GetMessagesRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatControllerImpl implements ChatController {
    private final ChatService chatService;

    @Override
    public ChatService getService() {
        return chatService;
    }

    @Override
    public <Res> Res createChatRoom(CreateChatRoomRequest createChatRoomRequest) {
        return getService().createChatRoom(createChatRoomRequest);
    }

    @Override
    public List<?> getChatsByPagination(Integer pageNumber) {
        return getService().getChatsByPagination(pageNumber);
    }

    @Override
    public List<?> getChatMessagesByPagination(GetMessagesRequest getMessagesRequest) {
        return getService().getChatMessagesByPagination(getMessagesRequest);
    }
}

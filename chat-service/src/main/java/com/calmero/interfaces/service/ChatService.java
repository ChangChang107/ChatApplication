package com.calmero.interfaces.service;

import com.calmero.model.request.CreateChatRoomRequest;
import com.calmero.model.request.GetMessagesRequest;
import com.calmero.model.request.PageNumberRequest;

import java.util.List;

public interface ChatService {
    List<?> getChatsByPagination(Integer pageNumber);

    List<?> getChatMessagesByPagination(GetMessagesRequest getMessagesRequest);

    <Res> Res createChatRoom(CreateChatRoomRequest createChatRoomRequest);
}

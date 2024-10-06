package com.calmero.interfaces.controller;

import com.calmero.interfaces.service.ChatService;
import com.calmero.model.request.CreateChatRoomRequest;
import com.calmero.model.request.GetMessagesRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(path = "/v1")
public interface ChatController extends ParentController<ChatService> {

    @PostMapping("/user/chat/room")
    <Res> Res createChatRoom(@Valid @RequestBody CreateChatRoomRequest createChatRoomRequest);

    @GetMapping("/user/chats")
    List<?> getChatsByPagination(@RequestParam("pageNumber") Integer pageNumber);

    @GetMapping("/user/chat/messages")
    List<?> getChatMessagesByPagination(@Valid @RequestBody GetMessagesRequest getMessagesRequest);
}

package com.gaaji.chat.controller;

import com.gaaji.chat.controller.dto.ChatRoom;
import com.gaaji.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ChatRoom> getAllChatRoomList() {
        List<ChatRoom> rooms = chatService.findAllRooms();
        return rooms;
    }

    @PostMapping(value = "/chat/send", headers = "user_id")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatRoom createChatRoom(String roomName, @RequestHeader("user_id") String userId) {
        return chatService.createRoom(roomName);
    }

    @GetMapping("/chat/room")
    @ResponseStatus(HttpStatus.OK)
    public ChatRoom enterChatRoom(String roomId) {
        return chatService.findRoomById(roomId);
    }

}

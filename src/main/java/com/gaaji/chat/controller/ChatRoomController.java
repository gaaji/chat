package com.gaaji.chat.controller;

import com.gaaji.chat.controller.dto.ChatRoom;
import com.gaaji.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    /** 채팅방 전체 리스트 반환 */
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ChatRoom> getAllChatRoomList() {
        // 유저 정보를 가져오는 작업? 헤더 안에 있는 유저 아이디

        // 사용자에 해당하는 채팅방 리스트를 가져옴.
        List<ChatRoom> rooms = chatService.findAllRooms();
        // 채팅방 전체 리스트 반환
        return rooms;
    }

    /** 채팅방 생성 */
    @PostMapping("/chat/createroom")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatRoom createChatRoom(String roomName) {
        return chatService.createRoom(roomName);
    }

    @GetMapping("/chat/room")
    @ResponseStatus(HttpStatus.OK)
    public ChatRoom enterChatRoom(String roomId) {
        return chatService.findRoomById(roomId);
    }

}

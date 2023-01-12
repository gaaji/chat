package com.gaaji.chat.controller;

import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final ChatService chatService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponseDto> roomList() {
        return chatService.findAllRooms();
    }
}

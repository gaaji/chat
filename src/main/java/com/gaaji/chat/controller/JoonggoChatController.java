package com.gaaji.chat.controller;

import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.service.JoonggoChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/joonggos")
public class JoonggoChatController {
    private final JoonggoChatService joonggoChatService;

    @PostMapping("/{joonggoId}/chat-room")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponseDto joonggoChatRoomSave(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId, @PathVariable String joonggoId) {
        return joonggoChatService.createDuoChatRoom(authId, joonggoId);
    }
}

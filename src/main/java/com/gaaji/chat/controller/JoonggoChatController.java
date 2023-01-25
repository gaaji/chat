package com.gaaji.chat.controller;

import com.gaaji.chat.controller.dto.JoonggoChatRoomSaveRequestDto;
import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.service.JoonggoChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/joonggo-chat")
public class JoonggoChatController {
    private final JoonggoChatService joonggoChatService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponseDto joonggoChatRoomSave(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId, @RequestBody JoonggoChatRoomSaveRequestDto dto) {
        return joonggoChatService.createDuoChatRoom(authId, dto);
    }

    @PostMapping("/{roomId}/chat-room/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void joonggoChatMemberLeave(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId, @PathVariable String roomId) {
//        joonggoChatService.leaveDuoChatRoom(authId, roomId);
    }
}

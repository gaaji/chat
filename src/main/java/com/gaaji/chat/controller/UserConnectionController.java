package com.gaaji.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaaji.chat.service.ChatRoomService;
import com.gaaji.chat.service.message.MessageHandlingService;
import com.gaaji.chat.service.message.dto.MessageSendEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserConnectionController {
    private final ChatRoomService chatRoomService;

    private final MessageHandlingService messageHandlingService;

    @PatchMapping("/users/{userId}/connection-status/{connectionStatus}")
    @ResponseStatus(HttpStatus.OK)
    public void patchUserConnectionStatus(@PathVariable String userId, @PathVariable String connectionStatus) {
        chatRoomService.patchUserConnectionStatus(userId, connectionStatus);
    }


}

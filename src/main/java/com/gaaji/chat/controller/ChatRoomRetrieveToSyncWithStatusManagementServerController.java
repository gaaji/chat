package com.gaaji.chat.controller;

import com.gaaji.chat.controller.dto.ChatRoomRetrieveToSyncWithStatusManagementServerResponseDto;
import com.gaaji.chat.service.ChatRoomRetrieveToSyncWithStatusManagementServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomRetrieveToSyncWithStatusManagementServerController {
    private final ChatRoomRetrieveToSyncWithStatusManagementServerService chatRoomRetrieveToSyncWithStatusManagementServerService;

    @GetMapping("/chat-rooms/{chatRoomId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatRoomRetrieveToSyncWithStatusManagementServerResponseDto chatRoom(@PathVariable String chatRoomId) {
        return chatRoomRetrieveToSyncWithStatusManagementServerService.retrieveChatRoom(chatRoomId);
    }
}

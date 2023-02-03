package com.gaaji.chat.controller.dto;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatRoomRetrieveToSyncWithStatusManagementServerResponseDto {
    private String roomId;
    private List<String> memberIds = new ArrayList<>();

    public static ChatRoomRetrieveToSyncWithStatusManagementServerResponseDto of(ChatRoom chatRoom) {
        ChatRoomRetrieveToSyncWithStatusManagementServerResponseDto dto = new ChatRoomRetrieveToSyncWithStatusManagementServerResponseDto();
        dto.roomId = chatRoom.getId();
        chatRoom.getMembers().forEach(user -> dto.getMemberIds().add(user.getId()));
        return dto;
    }
}

package com.gaaji.chat.service.dto;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomDeletedEventDto {
    private String chatRoomId;
    public static ChatRoomDeletedEventDto create(ChatRoom chatRoom) {
        return null;
    }
}

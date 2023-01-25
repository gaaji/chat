package com.gaaji.chat.service.dto;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomDeletedEventDto {
    private String chatRoomId;
    public static ChatRoomDeletedEventDto create(ChatRoom chatRoom) {
        ChatRoomDeletedEventDto dto = new ChatRoomDeletedEventDto();
        dto.chatRoomId = chatRoom.getId();
        return dto;
    }
}

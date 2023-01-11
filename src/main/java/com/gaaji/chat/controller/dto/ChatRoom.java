package com.gaaji.chat.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChatRoom {
    private String roomId;
    private String name;

    public static ChatRoom create(String roomName) {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .name(roomName)
                .build();
    }
}

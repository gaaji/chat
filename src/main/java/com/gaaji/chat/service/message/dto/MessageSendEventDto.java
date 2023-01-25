package com.gaaji.chat.service.message.dto;

import lombok.Getter;

@Getter
public class MessageSendEventDto {
    private String roomId;
    private String senderId;
    private String content;

    @Override
    public String toString() {
        return "MessageSendEventDto{" +
                "roomId='" + roomId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

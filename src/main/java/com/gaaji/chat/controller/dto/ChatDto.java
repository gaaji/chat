package com.gaaji.chat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

    public enum MessageType {
        ENTER, TALK, LEAVE;
    }

    private MessageType messageType;
    private String roomId;
    private String senderId;
    private String message;
    private String time;
}

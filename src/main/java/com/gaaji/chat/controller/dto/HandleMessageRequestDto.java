package com.gaaji.chat.controller.dto;

import lombok.Getter;

@Getter
public class HandleMessageRequestDto {
    private String roomId;
    private String senderId;
    private String content;
}
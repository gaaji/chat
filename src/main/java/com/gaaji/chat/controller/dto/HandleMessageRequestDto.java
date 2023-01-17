package com.gaaji.chat.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class HandleMessageRequestDto {
    @NotBlank
    private String roomId;
    @NotBlank
    private String senderId;
    @NotBlank
    private String content;
}

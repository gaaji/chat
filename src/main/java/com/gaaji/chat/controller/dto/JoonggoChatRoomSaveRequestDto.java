package com.gaaji.chat.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class JoonggoChatRoomSaveRequestDto {
    @NotBlank
    private String buyerId;
    @NotBlank
    private String joonggoId;
}

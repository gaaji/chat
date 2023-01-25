package com.gaaji.chat.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class JoonggoChatRoomSaveRequestDto {
    @NotBlank
    private String buyerId;
    @NotBlank
    private String joonggoId;

    public static JoonggoChatRoomSaveRequestDto create(String buyerId, String joonggoId) {
        JoonggoChatRoomSaveRequestDto dto = new JoonggoChatRoomSaveRequestDto();
        dto.buyerId = buyerId;
        dto.joonggoId = joonggoId;
        return dto;
    }
}

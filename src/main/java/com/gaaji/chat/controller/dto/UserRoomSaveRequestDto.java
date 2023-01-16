package com.gaaji.chat.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserRoomSaveRequestDto {
    @NotBlank
    private String roomId;

    public static UserRoomSaveRequestDto create(String roomId) {
        UserRoomSaveRequestDto dto = new UserRoomSaveRequestDto();
        dto.roomId = roomId;
        return dto;
    }
}

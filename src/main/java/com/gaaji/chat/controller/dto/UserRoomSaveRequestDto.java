package com.gaaji.chat.controller.dto;

import lombok.Getter;

@Getter
public class UserRoomSaveRequestDto {
    private String id;

    public static UserRoomSaveRequestDto create(String id) {
        UserRoomSaveRequestDto dto = new UserRoomSaveRequestDto();
        dto.id = id;
        return dto;
    }
}

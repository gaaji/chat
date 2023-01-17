package com.gaaji.chat.controller.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class RoomSaveRequestDto {
    private String name;
    private List<UserDto> members = new ArrayList<>();

    public static RoomSaveRequestDto create(String name, List<UserDto> members) {
        RoomSaveRequestDto roomSaveRequestDto = new RoomSaveRequestDto();
        roomSaveRequestDto.name = name;
        if(members != null) roomSaveRequestDto.members.addAll(members);
        return roomSaveRequestDto;
    }
    @Getter
    public static class UserDto {
        private String id;

        public static UserDto create(String id) {
            UserDto userDto = new UserDto();
            userDto.id = id;
            return userDto;
        }
    }
}


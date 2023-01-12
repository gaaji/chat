package com.gaaji.chat.controller.dto;

import com.gaaji.chat.domain.Room;
import com.gaaji.chat.domain.UserRoom;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoomResponseDto {
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private List<UserDto> users = new ArrayList<>();

    public static List<RoomResponseDto> listOf(List<Room> rooms) {
        List<RoomResponseDto> list = new ArrayList<>();
        for (Room room : rooms) {
            RoomResponseDto dto = new RoomResponseDto();
            dto.id = room.getId();
            dto.createdAt = room.getCreatedAt();
            dto.name = room.getName();
            dto.users = UserDto.listOf(room.getUserRooms());
            list.add(dto);
        }
        return list;
    }

    @Override
    public String toString() {
        return "RoomResponseDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", users=" + users +
                '}';
    }
}
class UserDto {
    private String id;

    public static List<UserDto> listOf(List<UserRoom> userRooms) {
        List<UserDto> dtos = new ArrayList<>();
        for (UserRoom userRoom : userRooms) {
            UserDto userDto = new UserDto();
            userDto.id = userRoom.getId();
            dtos.add(userDto);
        }
        return dtos;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                '}';
    }
}

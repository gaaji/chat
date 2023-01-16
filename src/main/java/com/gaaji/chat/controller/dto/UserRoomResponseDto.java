package com.gaaji.chat.controller.dto;

import com.gaaji.chat.domain.Room;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.UserRoom;
import lombok.Getter;

@Getter
public class UserRoomResponseDto {
    private String id;
    RoomDto room;
    UserDto user;

    public static UserRoomResponseDto of(UserRoom userRoom) {
        UserRoomResponseDto dto = new UserRoomResponseDto();
        dto.id = userRoom.getId();
        dto.user = UserDto.of(userRoom.getUser());
        dto.room = RoomDto.of(userRoom.getRoom());
        return dto;
    }

    @Getter
    public static class UserDto {
        private String id;

        public static UserDto of(User user) {
            UserDto dto = new UserDto();
            dto.id = user.getId();
            return dto;
        }
    }
    @Getter
    public static class RoomDto {
        private String id;

        public static RoomDto of(Room room) {
            RoomDto roomDto = new RoomDto();
            roomDto.id = room.getId();
            return roomDto;
        }
    }
}


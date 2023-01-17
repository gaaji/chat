package com.gaaji.chat.controller.dto;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.GroupChatMember;
import lombok.Getter;

@Getter
public class UserRoomResponseDto {
    private String id;
    RoomDto room;
    UserDto user;

    public static UserRoomResponseDto of(GroupChatMember groupChatMember) {
        UserRoomResponseDto dto = new UserRoomResponseDto();
        dto.id = groupChatMember.getId();
        dto.user = UserDto.of(groupChatMember.getMember());
        dto.room = RoomDto.of(groupChatMember.getChatRoom());
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

        public static RoomDto of(ChatRoom chatRoom) {
            RoomDto roomDto = new RoomDto();
            roomDto.id = chatRoom.getId();
            return roomDto;
        }
    }
}


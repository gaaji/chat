package com.gaaji.chat.controller.dto;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.GroupChatMember;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
public class RoomResponseDto {
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private List<UserDto> users = new ArrayList<>();

    public static List<RoomResponseDto> listOf(List<ChatRoom> chatRooms) {
        List<RoomResponseDto> list = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            list.add(of(chatRoom));
        }
        return list;
    }

    public static RoomResponseDto of(ChatRoom chatRoom) {
        RoomResponseDto dto = new RoomResponseDto();
        dto.id = chatRoom.getId();
        dto.createdAt = chatRoom.getCreatedAt();
        dto.name = chatRoom.getName();
        dto.users = UserDto.listOf(chatRoom.getGroupChatMembers());
        return dto;
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
    @Getter
    public static class UserDto {
        private String id;

        public static List<UserDto> listOf(List<GroupChatMember> groupChatMembers) {
            List<UserDto> dtos = new ArrayList<>();
            for (GroupChatMember groupChatMember : groupChatMembers) {
                UserDto userDto = new UserDto();
                userDto.id = groupChatMember.getMember().getId();
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
}

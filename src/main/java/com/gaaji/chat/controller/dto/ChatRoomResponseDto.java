package com.gaaji.chat.controller.dto;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
public class ChatRoomResponseDto {
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private List<UserDto> members = new ArrayList<>();

    public static List<ChatRoomResponseDto> listOf(List<ChatRoom> chatRooms) {
        List<ChatRoomResponseDto> list = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            list.add(of(chatRoom));
        }
        return list;
    }

    public static ChatRoomResponseDto of(ChatRoom chatRoom) {
        ChatRoomResponseDto dto = new ChatRoomResponseDto();
        dto.id = chatRoom.getId();
        dto.createdAt = chatRoom.getCreatedAt();
        dto.name = chatRoom.getName();
        dto.members = UserDto.listOf(chatRoom.getChatRoomMembers());
        return dto;
    }

    @Override
    public String toString() {
        return "RoomResponseDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", users=" + members +
                '}';
    }
    @Getter
    public static class UserDto {
        private String id;

        public static List<UserDto> listOf(List<ChatRoomMember> chatRoomMembers) {
            List<UserDto> dtos = new ArrayList<>();
            for (ChatRoomMember chatRoomMember : chatRoomMembers) {
                UserDto userDto = new UserDto();
                userDto.id = chatRoomMember.getMember().getId();
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

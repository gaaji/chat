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
    private String roomNameForYou;
    private LocalDateTime createdAt;
    private List<UserDto> members = new ArrayList<>();

    public static ChatRoomResponseDto of(ChatRoom chatRoom, String chatRoomNameForTheUser) {
        ChatRoomResponseDto dto = new ChatRoomResponseDto();
        dto.id = chatRoom.getId();
        dto.createdAt = chatRoom.getCreatedAt();
        dto.roomNameForYou = chatRoomNameForTheUser;
        dto.members = UserDto.listOf(chatRoom.getChatRoomMembers());
        return dto;
    }

    @Override
    public String toString() {
        return "RoomResponseDto{" +
                "id='" + id + '\'' +
                ", name='" + roomNameForYou + '\'' +
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

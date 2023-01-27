package com.gaaji.chat.service.dto;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatRoomCreatedEventDto {
    private String roomId;
    private List<String> memberIds = new ArrayList<>();

    public static ChatRoomCreatedEventDto create(ChatRoom chatRoom) {
        ChatRoomCreatedEventDto dto = new ChatRoomCreatedEventDto();
        dto.roomId = chatRoom.getId();
        for (User member : chatRoom.getMembers()) {
            dto.memberIds.add(member.getId());
        }
        return dto;
    }
}

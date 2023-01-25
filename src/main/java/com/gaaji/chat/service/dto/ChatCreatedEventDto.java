package com.gaaji.chat.service.dto;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatCreatedEventDto {
    private String roomId;
    private List<String> memberIds = new ArrayList<>();

    public static ChatCreatedEventDto create(ChatRoom duoChatRoom) {
        ChatCreatedEventDto dto = new ChatCreatedEventDto();
        dto.roomId = duoChatRoom.getId();
        for (User member : duoChatRoom.getMembers()) {
            dto.memberIds.add(member.getId());
        }
        return dto;
    }
}

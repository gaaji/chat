package com.gaaji.chat.service.dto;

import lombok.Getter;

@Getter
public class MemberAddedEventDto {
    private String chatRoomId;
    private String memberId;

    public static MemberAddedEventDto create(String chatRoomId, String memberId) {
        MemberAddedEventDto dto = new MemberAddedEventDto();
        dto.chatRoomId = chatRoomId;
        dto.memberId = memberId;
        return dto;
    }
}

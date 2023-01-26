package com.gaaji.chat.service.dto;

import com.gaaji.chat.domain.User;
import lombok.Getter;

@Getter
public class MemberLeftEventDto {
    private String id;

    public static MemberLeftEventDto create(User memberToLeave) {
        MemberLeftEventDto dto = new MemberLeftEventDto();
        dto.id = memberToLeave.getId();
        return dto;
    }
}

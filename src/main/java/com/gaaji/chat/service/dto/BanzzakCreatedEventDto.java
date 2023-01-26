package com.gaaji.chat.service.dto;

import com.gaaji.chat.domain.post.Banzzak;
import lombok.Getter;

@Getter
public class BanzzakCreatedEventDto {
    private String postId;

    public static BanzzakCreatedEventDto create(Banzzak banzzak) {
        BanzzakCreatedEventDto dto = new BanzzakCreatedEventDto();
        dto.postId = banzzak.getId();
        return dto;
    }
}

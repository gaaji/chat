package com.gaaji.chat.service.dto;

import com.gaaji.chat.domain.post.Banzzak;
import lombok.Getter;

@Getter
public class BanzzakCreatedEventDto {
    private String id;
    private String name;

    public static BanzzakCreatedEventDto create(Banzzak banzzak, String banzzakName) {
        BanzzakCreatedEventDto dto = new BanzzakCreatedEventDto();
        dto.id = banzzak.getId();
        dto.name = banzzakName;
        return dto;
    }
}

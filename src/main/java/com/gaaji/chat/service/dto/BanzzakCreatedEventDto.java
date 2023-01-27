package com.gaaji.chat.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gaaji.chat.domain.post.Banzzak;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // 선언한 필드 외의 데이터는 무시
public class BanzzakCreatedEventDto {
    private String id;
    public static BanzzakCreatedEventDto create(Banzzak banzzak) {
        BanzzakCreatedEventDto dto = new BanzzakCreatedEventDto();
        dto.id = banzzak.getId();
        return dto;
    }
}

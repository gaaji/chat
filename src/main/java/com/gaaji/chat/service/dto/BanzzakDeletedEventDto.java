package com.gaaji.chat.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // 선언한 필드 외의 데이터는 무시
public class BanzzakDeletedEventDto {
    private String id;

    public static BanzzakDeletedEventDto create(String id) {
        BanzzakDeletedEventDto dto = new BanzzakDeletedEventDto();
        dto.id = id;
        return dto;
    }
}

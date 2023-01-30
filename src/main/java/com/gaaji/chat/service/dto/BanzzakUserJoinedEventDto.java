package com.gaaji.chat.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BanzzakUserJoinedEventDto {
    private String postId;
    private String userId;

    public static BanzzakUserJoinedEventDto create(String postId, String userId) {
        BanzzakUserJoinedEventDto dto = new BanzzakUserJoinedEventDto();
        dto.postId = postId;
        dto.userId = userId;
        return dto;
    }
}

package com.gaaji.chat.service.dto;

import lombok.Getter;

@Getter
public class BanzzakUserLeftEventDto {
    private String postId;
    private String userId;

    public static BanzzakUserLeftEventDto create(String postId, String userId) {
        BanzzakUserLeftEventDto dto = new BanzzakUserLeftEventDto();
        dto.postId = postId;
        dto.userId = userId;
        return dto;
    }
}

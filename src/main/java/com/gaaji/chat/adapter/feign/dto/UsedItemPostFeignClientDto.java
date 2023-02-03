package com.gaaji.chat.adapter.feign.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsedItemPostFeignClientDto {
    private String postId;
    private String sellerId;
}

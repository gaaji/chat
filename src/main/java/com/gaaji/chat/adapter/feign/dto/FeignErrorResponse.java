package com.gaaji.chat.adapter.feign.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;

@Getter
public class FeignErrorResponse {
    private String timeStamp;
    private HttpStatus httpStatus;
    private String errorCode;
    private String errorName;
    private String errorMessage;
    private String path;

    public static FeignErrorResponse of (FeignException e) throws JsonProcessingException {
        String body = StandardCharsets.UTF_8.decode(e.responseBody().orElseThrow(() -> e)).toString();
        return new ObjectMapper().readValue(body, FeignErrorResponse.class);
    }
}

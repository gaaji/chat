package com.gaaji.chat.controller;

import com.gaaji.chat.controller.dto.HandleMessageRequestDto;
import com.gaaji.chat.service.MessageHandlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageHandlingController {
    private final MessageHandlingService messageHandlingService;

    @PostMapping(value = "/push-to-offline-members")
    @ResponseStatus(HttpStatus.OK)
    public void handleMessage(@RequestBody @Validated HandleMessageRequestDto dto) {
        // must implement
    }
}

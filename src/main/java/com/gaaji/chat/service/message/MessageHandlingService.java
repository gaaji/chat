package com.gaaji.chat.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaaji.chat.service.message.dto.MessageSendEventDto;

public interface MessageHandlingService {
    void handleChatted(String body) throws JsonProcessingException;

}

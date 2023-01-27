package com.gaaji.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GroupChatService {
    void handleBanzzakCreated(String body) throws JsonProcessingException;

    void handleBanzzakUserJoined(String body) throws JsonProcessingException;
}

package com.gaaji.chat.service.connection;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ConnectionEventHandlingService {
    void handleConnected(String body) throws JsonProcessingException;

    void handleDisConnected(String body) throws JsonProcessingException;
}

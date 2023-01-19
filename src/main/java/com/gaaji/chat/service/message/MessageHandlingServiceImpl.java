package com.gaaji.chat.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chat.service.message.dto.MessageSendEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageHandlingServiceImpl implements MessageHandlingService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Override
    @KafkaListener(topics = "chat-chatted", errorHandler = "kafkaErrorHandler")
    public void handleChatted(String body) throws JsonProcessingException {
        MessageSendEventDto messageSendEventDto = new ObjectMapper().readValue(body, MessageSendEventDto.class);
        System.out.println("event occurred: " + messageSendEventDto);
    }
}

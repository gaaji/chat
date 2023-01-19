package com.gaaji.chat.service.connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chat.domain.ConnectionStatus;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.execption.UserNotFoundException;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.service.connection.dto.ConnectedEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConnectionEventHandlingServiceImpl implements ConnectionEventHandlingService {
    private final UserRepository userRepository;
    @Override
    @Transactional
    @KafkaListener(topics = "chat-connected", errorHandler = "kafkaErrorHandler")
    public void handleConnected(String body) throws JsonProcessingException {
        ConnectedEventDto connectedEventDto = new ObjectMapper().readValue(body, ConnectedEventDto.class);
        User user = userRepository.findById(connectedEventDto.getUserId()).orElseThrow(UserNotFoundException::new);
        user.setConnectionStatus(ConnectionStatus.ONLINE);
    }

    @Override
    @Transactional
    @KafkaListener(topics = "chat-disconnected", errorHandler = "kafkaErrorHandler")
    public void handleDisConnected(String body) throws JsonProcessingException {
        ConnectedEventDto connectedEventDto = new ObjectMapper().readValue(body, ConnectedEventDto.class);
        User user = userRepository.findById(connectedEventDto.getUserId()).orElseThrow(UserNotFoundException::new);
        user.setConnectionStatus(ConnectionStatus.OFFLINE);
    }
}

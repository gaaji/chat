package com.gaaji.chat.service;

import com.gaaji.chat.domain.ConnectionStatus;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.repository.RoomRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.repository.UserRoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatServiceImplTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoomRepository userRoomRepository;
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ChatService chatService;

    @Test
    void patchUserConnectionStatus() {
        // given
        String userId = UUID.randomUUID().toString();
        String connectionStatus = "ONLINE";
        userRepository.save(new User(userId, ConnectionStatus.valueOf(connectionStatus)));

        // when
        chatService.patchUserConnectionStatus(userId, "OFFLINE");
        User user = userRepository.findById(userId).get();
        // then
        Assertions.assertEquals(user.getConnectionStatus(), ConnectionStatus.valueOf("OFFLINE"));
    }
}
package com.gaaji.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.post.Banzzak;
import com.gaaji.chat.domain.post.Joonggo;
import com.gaaji.chat.domain.post.Post;
import com.gaaji.chat.execption.ChatRoomForTheBanzzakAlreadyExistsException;
import com.gaaji.chat.repository.ChatRoomRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.service.dto.BanzzakCreatedEventDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;
@SpringBootTest
@Transactional
class GroupChatServiceImplTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    GroupChatService groupChatService;

    @Autowired
    EntityManager em;

    User newUser() {
        return userRepository.save(new User(UUID.randomUUID().toString()));
    }

    private Banzzak newBanzzak(User owner) {
        Banzzak banzzak = Post.randomBanzzakForTest(owner);
        em.persist(banzzak);
        return banzzak;
    }
    @Test
    void handleBanzzakCreated() throws JsonProcessingException {
        // given
        User groupOwner = newUser();
        Banzzak banzzak = newBanzzak(groupOwner);

        // when
        groupChatService.handleBanzzakCreated(new ObjectMapper().writeValueAsString(BanzzakCreatedEventDto.create(banzzak)));

        // then
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByPost(banzzak);
        Assertions.assertTrue(chatRoomOptional.isPresent());
        ChatRoom chatRoom = chatRoomOptional.get();
        Assertions.assertEquals(groupOwner, chatRoom.getPost().getOwner());
        Assertions.assertThrows(ChatRoomForTheBanzzakAlreadyExistsException.class, () -> groupChatService.handleBanzzakCreated(new ObjectMapper().writeValueAsString(BanzzakCreatedEventDto.create(banzzak))));

    }
}
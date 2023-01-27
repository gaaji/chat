package com.gaaji.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import com.gaaji.chat.domain.post.Banzzak;
import com.gaaji.chat.domain.post.Post;
import com.gaaji.chat.execption.ChatRoomForTheBanzzakAlreadyExistsException;
import com.gaaji.chat.repository.BanzzakRepository;
import com.gaaji.chat.repository.ChatRoomRepository;
import com.gaaji.chat.repository.PostRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.service.dto.BanzzakCreatedEventDto;
import com.gaaji.chat.service.dto.BanzzakUserJoinedEventDto;
import com.gaaji.chat.service.dto.BanzzakUserLeftEventDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
    @Autowired
    private BanzzakRepository banzzakRepository;
    @Autowired
    private PostRepository postRepository;

    User newUser() {
        return userRepository.save(new User(UUID.randomUUID().toString()));
    }

    private Banzzak newBanzzak(User owner) {
        Banzzak banzzak = Post.randomBanzzakForTest(owner, "달리기 모임");
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

        Post post = postRepository.findById(banzzak.getId()).get();
        Assertions.assertTrue(post instanceof Banzzak);
        ChatRoom chatRoom = ((Banzzak)post).getChatRoom();
        Assertions.assertTrue(chatRoom !=null);

        Assertions.assertEquals(groupOwner, chatRoom.getPost().getOwner());
        Assertions.assertThrows(ChatRoomForTheBanzzakAlreadyExistsException.class, () -> groupChatService.handleBanzzakCreated(new ObjectMapper().writeValueAsString(BanzzakCreatedEventDto.create(banzzak))));
    }

    @Test
    void handleBanzzakUserJoined() throws JsonProcessingException {
        // given
        User banzzakOwner = newUser();
        User userToJoin = newUser();
        Banzzak newBanzzak = newBanzzak(banzzakOwner);

        // when
        ObjectMapper objectMapper = new ObjectMapper();
        groupChatService.handleBanzzakCreated(new ObjectMapper().writeValueAsString(BanzzakCreatedEventDto.create(newBanzzak)));
        groupChatService.handleBanzzakUserJoined(objectMapper.writeValueAsString(BanzzakUserJoinedEventDto.create(newBanzzak.getId(), userToJoin.getId())));

        // then
        Banzzak banzzak = banzzakRepository.findById(newBanzzak.getId()).get();
        ChatRoom chatRoom = banzzak.getChatRoom();
        Assertions.assertTrue(chatRoom.getChatRoomMembers().size() == 2);
        chatRoom.getChatRoomMembers().forEach(chatRoomMember -> Assertions.assertTrue(chatRoomMember.getRoomName().equals(banzzak.getName())));
    }

    @Test
    void handleBanzzakUserLeft() throws JsonProcessingException {
        // given
        User banzzakOwner = newUser();
        User userToJoin = newUser();
        Banzzak newBanzzak = newBanzzak(banzzakOwner);
        ObjectMapper objectMapper = new ObjectMapper();
        groupChatService.handleBanzzakCreated(new ObjectMapper().writeValueAsString(BanzzakCreatedEventDto.create(newBanzzak)));
        groupChatService.handleBanzzakUserJoined(objectMapper.writeValueAsString(BanzzakUserJoinedEventDto.create(newBanzzak.getId(), userToJoin.getId())));

        // when
        groupChatService.handleBanzzakUserLeft(objectMapper.writeValueAsString(BanzzakUserLeftEventDto.create(newBanzzak.getId(), userToJoin.getId())));

        // then
        Banzzak banzzak = banzzakRepository.findById(newBanzzak.getId()).get();
        for (ChatRoomMember chatRoomMember : banzzak.getChatRoom().getChatRoomMembers()) {
            if (chatRoomMember.getMember().equals(userToJoin)) {
                Assertions.assertTrue(chatRoomMember.isLeft());
                break;
            }
        }
    }
}
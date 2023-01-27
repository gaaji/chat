package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.JoonggoChatRoomSaveRequestDto;
import com.gaaji.chat.controller.dto.ChatRoomResponseDto;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import com.gaaji.chat.domain.post.Joonggo;
import com.gaaji.chat.domain.post.Post;
import com.gaaji.chat.execption.JoonggoChatRoomForTheBuyerAlreadyExistsException;
import com.gaaji.chat.repository.ChatRoomRepository;
import com.gaaji.chat.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

@SpringBootTest
@Transactional
class JoonggoChatServiceImplTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    JoonggoChatService joonggoChatService;

    @Autowired
    EntityManager em;

    User newUser() {
        return userRepository.save(new User(UUID.randomUUID().toString()));
    }

    @Test
    void createDuoChatRoom() {
        // given
        User seller = newUser();
        User buyer = newUser();
        Joonggo joonggo = newJoonggo(seller);

        // 성공
        ChatRoomResponseDto dto = joonggoChatService.createDuoChatRoom(buyer.getId(), JoonggoChatRoomSaveRequestDto.create(buyer.getId(), joonggo.getId()));
        Assertions.assertEquals(2, dto.getMembers().size());
        dto.getMembers().forEach(userDto -> {
            String id = userDto.getId();
            Assertions.assertTrue(id.equals(seller.getId()) || id.equals(buyer.getId()));
        });

        // 중복 생성 요청 시 에러 발생
        Assertions.assertThrows(JoonggoChatRoomForTheBuyerAlreadyExistsException.class, () -> joonggoChatService.createDuoChatRoom(buyer.getId(), JoonggoChatRoomSaveRequestDto.create(buyer.getId(), joonggo.getId())));
    }

    private Joonggo newJoonggo(User seller) {
        Joonggo joonggo = Post.randomJoonggoForTest(seller);
        em.persist(joonggo);
        return joonggo;
    }

    @Test
    @Transactional
    void leaveDuoChatRoom() {
        // given
        User seller = newUser();
        User buyer = newUser();
        Joonggo joonggo = newJoonggo(seller);
        ChatRoomResponseDto dto = joonggoChatService.createDuoChatRoom(buyer.getId(), JoonggoChatRoomSaveRequestDto.create(buyer.getId(), joonggo.getId()));

        // when
        joonggoChatService.leaveDuoChatRoom(buyer.getId(), dto.getId(), buyer.getId());
        ChatRoom chatRoom = chatRoomRepository.findById(dto.getId()).get();

        // then
        for (ChatRoomMember chatRoomMember : chatRoom.getChatRoomMembers()) {
            if(chatRoomMember.getMember().getId().equals(buyer.getId())) Assertions.assertEquals(true, chatRoomMember.isLeft());
            break;
        }

    }
}
package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.JoonggoChatRoomSaveRequestDto;
import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.domain.ConnectionStatus;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import com.gaaji.chat.domain.post.Joonggo;
import com.gaaji.chat.domain.post.Post;
import com.gaaji.chat.execption.JoonggoChatRoomForTheBuyerAlreadyExistsException;
import com.gaaji.chat.repository.ChatRoomRepository;
import com.gaaji.chat.repository.GroupChatMemberRepository;
import com.gaaji.chat.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JoonggoChatServiceImplTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupChatMemberRepository groupChatMemberRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    GroupChatMemberService groupChatMemberService;

    @Autowired
    JoonggoChatService joonggoChatService;

    @Autowired
    EntityManager em;

    static int randRoomNum = 0;
    private ChatRoom newRoom() {
        return chatRoomRepository.save(ChatRoom.createGroupChatRoom(UUID.randomUUID().toString(), "room" + randRoomNum++));
    }

    User newUser() {
        return userRepository.save(new User(UUID.randomUUID().toString(), ConnectionStatus.OFFLINE));
    }

    ChatRoomMember newUserRoom(User user, ChatRoom chatRoom) {
        return groupChatMemberRepository.save(ChatRoomMember.create(UUID.randomUUID().toString(), user, chatRoom));
    }

    @Test
    void createDuoChatRoom() {
        // given
        User seller = newUser();
        User buyer = newUser();
        Joonggo joonggo = newJoonggo(seller);

        // 성공
        RoomResponseDto dto = joonggoChatService.createDuoChatRoom(buyer.getId(), JoonggoChatRoomSaveRequestDto.create(buyer.getId(), joonggo.getId()));
        Assertions.assertEquals(2, dto.getUsers().size());
        dto.getUsers().forEach(userDto -> {
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
    void leaveDuoChatRoom() {
    }
}
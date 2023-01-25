package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.controller.dto.RoomSaveRequestDto;
import com.gaaji.chat.controller.dto.UserRoomResponseDto;
import com.gaaji.chat.controller.dto.UserRoomSaveRequestDto;
import com.gaaji.chat.domain.ConnectionStatus;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import com.gaaji.chat.execption.NotYourUserRoomException;
import com.gaaji.chat.execption.GroupChatMemberNotFoundException;
import com.gaaji.chat.repository.ChatRoomRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.repository.GroupChatMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class UserChatChatChatRoomServiceImplTest {
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
    void saveForJoining() {
        // given
        User user = newUser();
        User userToJoin = newUser();
        RoomResponseDto room = chatRoomService.saveRoomForUser(user.getId(), RoomSaveRequestDto.create("room", new ArrayList<>()));

        // when
        groupChatMemberService.saveForJoining(userToJoin.getId(), UserRoomSaveRequestDto.create(room.getId()));
        List<RoomResponseDto> roomsByUserId = chatRoomService.findRoomsByUserId(userToJoin.getId());

        // then
        Assertions.assertEquals(1, roomsByUserId.size());
        for (RoomResponseDto dto : roomsByUserId) {
            Assertions.assertEquals("room", dto.getName());
        }
        Assertions.assertDoesNotThrow(() -> chatRoomService.findRoomByRoomId(userToJoin.getId(), room.getId()));
    }

    @Test
    void findByUserRoomId() {
        // given
        User user = newUser();
        User otherUser = newUser();
        ChatRoom chatRoom = newRoom();
        UserRoomResponseDto userRoomDto = groupChatMemberService.saveForJoining(user.getId(), UserRoomSaveRequestDto.create(chatRoom.getId()));

        // when
        UserRoomResponseDto byUserRoomId = groupChatMemberService.findByUserRoomId(user.getId(), userRoomDto.getId());

        // then
        Assertions.assertEquals(userRoomDto.getId(), byUserRoomId.getId());
        Assertions.assertThrows(NotYourUserRoomException.class, () -> groupChatMemberService.findByUserRoomId(otherUser.getId(), userRoomDto.getId()));
    }

    @Test
    void delete() {
        // given
        User user = newUser();
        User otherUser = newUser();
        ChatRoom chatRoom = newRoom();
        UserRoomResponseDto userRoomDto = groupChatMemberService.saveForJoining(user.getId(), UserRoomSaveRequestDto.create(chatRoom.getId()));

        // when
        groupChatMemberService.delete(user.getId(), userRoomDto.getId());

        // then
        Assertions.assertThrows(GroupChatMemberNotFoundException.class, () -> groupChatMemberService.findByUserRoomId(otherUser.getId(), userRoomDto.getId()));
    }
}
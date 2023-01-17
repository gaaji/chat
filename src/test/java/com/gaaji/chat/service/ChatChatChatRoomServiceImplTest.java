package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.controller.dto.RoomSaveRequestDto;
import com.gaaji.chat.controller.dto.UserRoomSaveRequestDto;
import com.gaaji.chat.domain.ConnectionStatus;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.GroupChatMember;
import com.gaaji.chat.domain.chatroom.GroupChatRoom;
import com.gaaji.chat.execption.NotYourRoomException;
import com.gaaji.chat.execption.ChatRoomNotFoundException;
import com.gaaji.chat.execption.UserNotFoundException;
import com.gaaji.chat.repository.ChatRoomRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.repository.GroupChatMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
class ChatChatChatRoomServiceImplTest {
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

    GroupChatMember newUserRoom(User user, ChatRoom chatRoom) {
        return groupChatMemberRepository.save(GroupChatMember.create(UUID.randomUUID().toString(), user, chatRoom));
    }

    @Test
    void createUser() {
        User user = newUser();
        User some = userRepository.findById(user.getId()).get();
        Assertions.assertEquals(user, some);
    }

    @Test
    void createRoom() {
        ChatRoom chatRoom = newRoom();
        ChatRoom byId = chatRoomRepository.findById(chatRoom.getId()).get();
        Assertions.assertEquals(chatRoom, byId);
    }

    @Test
    void createUserRoom() {
        User user = newUser();
        ChatRoom chatRoom = newRoom();
        GroupChatMember groupChatMember = newUserRoom(user, chatRoom);
        GroupChatMember byUserAndRoom = groupChatMemberRepository.findByMemberAndChatRoom(user, (GroupChatRoom) chatRoom).get();
        Assertions.assertEquals(groupChatMember, byUserAndRoom);
    }

    @Test
    void patchUserConnectionStatus() {
        // given
        String userId = UUID.randomUUID().toString();
        String connectionStatus = "ONLINE";
        userRepository.save(new User(userId, ConnectionStatus.valueOf(connectionStatus)));

        // when
        chatRoomService.patchUserConnectionStatus(userId, "OFFLINE");
        User user = userRepository.findById(userId).get();
        // then
        Assertions.assertEquals(user.getConnectionStatus(), ConnectionStatus.valueOf("OFFLINE"));
    }

    @Test
    void findRoomByRoomId() {
        // given
        User user = newUser();
        RoomResponseDto room = chatRoomService.saveRoomForUser(user.getId(), RoomSaveRequestDto.create("room", new ArrayList<>()));
        ChatRoom otherChatRoom = newRoom();

        // when
        RoomResponseDto roomByRoomId = chatRoomService.findRoomByRoomId(user.getId(), room.getId());

        // then
        Assertions.assertEquals(room.getId(), roomByRoomId.getId());
        Assertions.assertEquals(room.getName(), roomByRoomId.getName());
        Assertions.assertEquals(room.getCreatedAt(), roomByRoomId.getCreatedAt());
        Assertions.assertThrows(UserNotFoundException.class, () -> chatRoomService.findRoomByRoomId("asdf", room.getId()));
        Assertions.assertThrows(ChatRoomNotFoundException.class, () -> chatRoomService.findRoomByRoomId(user.getId(), "asdf"));
        Assertions.assertThrows(NotYourRoomException.class, () -> chatRoomService.findRoomByRoomId(user.getId(), otherChatRoom.getId()));
    }

    @Test
    void findRoomsByUserId() {
        // given
        User user = newUser();
        for (int i = 0; i < 3; i++) {
            chatRoomService.saveRoomForUser(user.getId(), RoomSaveRequestDto.create("room" + i, null));
        }

        // when
        List<RoomResponseDto> roomsByUserId = chatRoomService.findRoomsByUserId(user.getId());

        // then
        Assertions.assertEquals(3, roomsByUserId.size());
        System.out.println("expected id: " + user.getId());
        for (RoomResponseDto roomResponseDto : roomsByUserId) {
            System.out.println("roomid: " + roomResponseDto.getId());
            for (RoomResponseDto.UserDto userDto : roomResponseDto.getUsers()) {
                System.out.println("actual id: " + userDto.getId());
                Assertions.assertEquals(user.getId(), userDto.getId());
            }
            Assertions.assertTrue(roomResponseDto.getName().contains("room"));
        }
    }

    @Test
    void saveRoomForUser() {
        // given
        User user = newUser();

        List<RoomSaveRequestDto.UserDto> members = new ArrayList<>();
        User user1 = newUser();
        User user2 = newUser();
        User user3 = newUser();
        members.add(RoomSaveRequestDto.UserDto.create(user1.getId()));
        members.add(RoomSaveRequestDto.UserDto.create(user2.getId()));
        members.add(RoomSaveRequestDto.UserDto.create(user3.getId()));

        // when
        RoomResponseDto roomResponseDto = chatRoomService.saveRoomForUser(user.getId(), RoomSaveRequestDto.create("name", members));

        // then
        Assertions.assertEquals(roomResponseDto.getName(), "name");
        Assertions.assertEquals(roomResponseDto.getUsers().size(), 4);

    }

    @Test
    void joinRoom() {
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
}
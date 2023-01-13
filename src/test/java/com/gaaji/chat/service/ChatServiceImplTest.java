package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.domain.ConnectionStatus;
import com.gaaji.chat.domain.Room;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.UserRoom;
import com.gaaji.chat.execption.RoomNotFound;
import com.gaaji.chat.repository.RoomRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.repository.UserRoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    static int randRoomNum = 0;
    private Room newRoom() {
        return roomRepository.save(Room.create(UUID.randomUUID().toString(), "room" + randRoomNum++));
    }

    User newUser() {
        return userRepository.save(new User(UUID.randomUUID().toString(), ConnectionStatus.OFFLINE));
    }

    UserRoom newUserRoom(User user, Room room) {
        return userRoomRepository.save(UserRoom.create(UUID.randomUUID().toString(), user, room));
    }

    @Test
    void createUser() {
        User user = newUser();
        User some = userRepository.findById(user.getId()).get();
        Assertions.assertEquals(user, some);
    }

    @Test
    void createRoom() {
        Room room = newRoom();
        Room byId = roomRepository.findById(room.getId()).get();
        Assertions.assertEquals(room, byId);
    }

    @Test
    void createUserRoom() {
        User user = newUser();
        Room room = newRoom();
        UserRoom userRoom = newUserRoom(user, room);
        UserRoom byUserAndRoom = userRoomRepository.findByUserAndRoom(user, room).get();
        Assertions.assertEquals(userRoom, byUserAndRoom);
    }

    @Test
    void findAllRooms() {
        // given
        User user1 = newUser();
        User user2 = newUser();
        Room room1 = newRoom();
        Room room2 = newRoom();
        UserRoom userRoom11 = newUserRoom(user1, room1);
        UserRoom userRoom22 = newUserRoom(user2, room2);
        UserRoom userRoom12 = newUserRoom(user1, room2);

        // when
        List<RoomResponseDto> allRooms = chatService.findAllRooms();

        // then
        Assertions.assertEquals(2, allRooms.size());
        System.out.println("findAllRooms -> allRooms: " + allRooms.getClass() + " " + allRooms);
    }

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

    @Test
    void findRoomByRoomId() {
        // given
        Room room = newRoom();

        // when
        RoomResponseDto roomByRoomId = chatService.findRoomByRoomId(room.getId());

        // then
        Assertions.assertEquals(room.getId(), roomByRoomId.getId());
        Assertions.assertEquals(room.getName(), roomByRoomId.getName());
        Assertions.assertEquals(room.getCreatedAt(), roomByRoomId.getCreatedAt());


        // when
        // then
        Assertions.assertThrows(RoomNotFound.class, () -> chatService.findRoomByRoomId("asdf"));
    }
}
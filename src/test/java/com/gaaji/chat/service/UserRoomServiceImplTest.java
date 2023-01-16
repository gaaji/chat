package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.controller.dto.RoomSaveRequestDto;
import com.gaaji.chat.controller.dto.UserRoomResponseDto;
import com.gaaji.chat.controller.dto.UserRoomSaveRequestDto;
import com.gaaji.chat.domain.ConnectionStatus;
import com.gaaji.chat.domain.Room;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.UserRoom;
import com.gaaji.chat.execption.NotYourUserRoomException;
import com.gaaji.chat.execption.UserRoomNotFoundException;
import com.gaaji.chat.repository.RoomRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.repository.UserRoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRoomServiceImplTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoomRepository userRoomRepository;
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomService roomService;
    @Autowired
    UserRoomService userRoomService;

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
    void saveForJoining() {
        // given
        User user = newUser();
        User userToJoin = newUser();
        RoomResponseDto room = roomService.saveRoomForUser(user.getId(), RoomSaveRequestDto.create("room", new ArrayList<>()));

        // when
        userRoomService.saveForJoining(userToJoin.getId(), UserRoomSaveRequestDto.create(room.getId()));
        List<RoomResponseDto> roomsByUserId = roomService.findRoomsByUserId(userToJoin.getId());

        // then
        Assertions.assertEquals(1, roomsByUserId.size());
        for (RoomResponseDto dto : roomsByUserId) {
            Assertions.assertEquals("room", dto.getName());
        }
        Assertions.assertDoesNotThrow(() -> roomService.findRoomByRoomId(userToJoin.getId(), room.getId()));
    }

    @Test
    void findByUserRoomId() {
        // given
        User user = newUser();
        User otherUser = newUser();
        Room room = newRoom();
        UserRoomResponseDto userRoomDto = userRoomService.saveForJoining(user.getId(), UserRoomSaveRequestDto.create(room.getId()));

        // when
        UserRoomResponseDto byUserRoomId = userRoomService.findByUserRoomId(user.getId(), userRoomDto.getId());

        // then
        Assertions.assertEquals(userRoomDto.getId(), byUserRoomId.getId());
        Assertions.assertThrows(NotYourUserRoomException.class, () -> userRoomService.findByUserRoomId(otherUser.getId(), userRoomDto.getId()));
    }

    @Test
    void delete() {
        // given
        User user = newUser();
        User otherUser = newUser();
        Room room = newRoom();
        UserRoomResponseDto userRoomDto = userRoomService.saveForJoining(user.getId(), UserRoomSaveRequestDto.create(room.getId()));

        // when
        userRoomService.delete(user.getId(), userRoomDto.getId());

        // then
        Assertions.assertThrows(UserRoomNotFoundException.class, () -> userRoomService.findByUserRoomId(otherUser.getId(), userRoomDto.getId()));
    }
}
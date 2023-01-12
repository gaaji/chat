package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.ChatRoom;
import com.gaaji.chat.domain.ConnectionStatus;
import com.gaaji.chat.domain.Room;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.UserRoom;
import com.gaaji.chat.execption.RoomNotFound;
import com.gaaji.chat.execption.UserNotFoundException;
import com.gaaji.chat.repository.RoomRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;

    public User findUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public ChatRoom findRoomById(String roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFound::new);
        return ChatRoom.builder().roomId(room.getId()).name(room.getName()).build();
    }

    public List<ChatRoom> findAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        List<ChatRoom> chatRooms = new ArrayList<>();
        for (Room room : rooms) {
            chatRooms.add(ChatRoom.builder().roomId(room.getId()).name(room.getName()).build());
        }
        return chatRooms;
    }

    /** 채팅방 개설 */
    @Transactional
    public ChatRoom createRoom(String roomName) {
        Room room = Room.create(UUID.randomUUID().toString(), roomName);
        roomRepository.save(room);
        return ChatRoom.builder().roomId(room.getId()).name(room.getName()).build();
    }

    /** 채팅방 입장 */
    @Transactional
    public void addUserToRoom(String roomId, String userId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFound::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserRoom userRoom = UserRoom.create(user, room);
        userRoomRepository.save(userRoom);
    }

    /** 채팅방 나가기 */
    @Transactional
    public void removeUserFromRoom(String roomId, String userId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFound::new);
        for (UserRoom userRoom : room.getUserRooms()) {
            if(userRoom.getUser().getId().equals(userId)) {
                userRoomRepository.delete(userRoom);
                return;
            }
        }
    }

    @Override
    @Transactional
    public void patchUserConnectionStatus(String userId, String connectionStatus) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setConnectionStatus(ConnectionStatus.valueOf(connectionStatus));
    }
}

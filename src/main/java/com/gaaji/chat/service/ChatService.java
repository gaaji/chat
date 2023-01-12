package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.ChatRoom;
import com.gaaji.chat.domain.User;

import java.util.List;

public interface ChatService {
    User findUserById(String userId);
    ChatRoom findRoomById(String roomId);
    List<ChatRoom> findAllRooms();
    ChatRoom createRoom(String roomName);
    void addUserToRoom(String roomId, String userId);
    void removeUserFromRoom(String roomId, String userId);

    void patchUserConnectionStatus(String userId, String connectionStatus);
}

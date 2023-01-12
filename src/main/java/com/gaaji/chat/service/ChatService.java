package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.ChatRoom;
import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.domain.User;

import java.util.List;

public interface ChatService {
    List<RoomResponseDto> findAllRooms();
    void patchUserConnectionStatus(String userId, String connectionStatus);
}

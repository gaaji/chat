package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.RoomResponseDto;

import java.util.List;

public interface ChatService {
    List<RoomResponseDto> findAllRooms();
    void patchUserConnectionStatus(String userId, String connectionStatus);

    RoomResponseDto findRoomByRoomId(String roomId);
}

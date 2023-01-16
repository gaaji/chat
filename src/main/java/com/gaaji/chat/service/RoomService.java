package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.controller.dto.RoomSaveRequestDto;

import java.util.List;

public interface RoomService {
    void patchUserConnectionStatus(String userId, String connectionStatus);

    RoomResponseDto findRoomByRoomId(String ownerId, String roomId);

    RoomResponseDto saveRoomForUser(String userId, RoomSaveRequestDto dto);

    List<RoomResponseDto> findRoomsByUserId(String userId);
}

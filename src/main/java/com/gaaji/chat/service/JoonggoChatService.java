package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.RoomResponseDto;

public interface JoonggoChatService {

    RoomResponseDto createDuoChatRoom(String authId, String joonggoId);
}

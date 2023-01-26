package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.JoonggoChatRoomSaveRequestDto;
import com.gaaji.chat.controller.dto.ChatRoomResponseDto;

public interface JoonggoChatService {

    ChatRoomResponseDto createDuoChatRoom(String authId, JoonggoChatRoomSaveRequestDto dto);

    void leaveDuoChatRoom(String authId, String chatRoomId, String memberIdToLeave);
}

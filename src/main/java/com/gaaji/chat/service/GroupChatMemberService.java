package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.UserRoomSaveRequestDto;
import com.gaaji.chat.controller.dto.UserRoomResponseDto;

public interface GroupChatMemberService {
    UserRoomResponseDto saveForJoining(String userId, UserRoomSaveRequestDto dto);

    void delete(String userId, String userRoomId);

    UserRoomResponseDto findByUserRoomId(String userId, String userRoomId);
}

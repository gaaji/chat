package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.ChatRoomRetrieveToSyncWithStatusManagementServerResponseDto;

public interface ChatRoomRetrieveToSyncWithStatusManagementServerService {

    ChatRoomRetrieveToSyncWithStatusManagementServerResponseDto retrieveChatRoom(String chatRoomId);
}

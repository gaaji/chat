package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.ChatRoomRetrieveToSyncWithStatusManagementServerResponseDto;
import com.gaaji.chat.execption.ChatRoomNotFoundException;
import com.gaaji.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomRetrieveToSyncWithStatusManagementServerServiceImpl implements ChatRoomRetrieveToSyncWithStatusManagementServerService {
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoomRetrieveToSyncWithStatusManagementServerResponseDto retrieveChatRoom(String chatRoomId) {
        return ChatRoomRetrieveToSyncWithStatusManagementServerResponseDto.of(chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new));
    }
}

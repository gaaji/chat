package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.UserRoomSaveRequestDto;
import com.gaaji.chat.controller.dto.UserRoomResponseDto;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.GroupChatMember;
import com.gaaji.chat.execption.NotYourUserRoomException;
import com.gaaji.chat.execption.RoomNotFoundException;
import com.gaaji.chat.execption.UserNotFoundException;
import com.gaaji.chat.execption.UserRoomNotFoundException;
import com.gaaji.chat.repository.RoomRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class UserRoomServiceImpl implements UserRoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;

    @Override
    @Transactional
    public UserRoomResponseDto saveForJoining(String userId, UserRoomSaveRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        ChatRoom chatRoom = roomRepository.findById(dto.getRoomId()).orElseThrow(RoomNotFoundException::new);
        GroupChatMember groupChatMember = userRoomRepository.save(GroupChatMember.create(UUID.randomUUID().toString(), user, chatRoom));
        return UserRoomResponseDto.of(groupChatMember);
    }

    @Override
    @Transactional
    public void delete(String userId, String userRoomId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        GroupChatMember groupChatMember = userRoomRepository.findById(userRoomId).orElseThrow(UserRoomNotFoundException::new);
        if(!groupChatMember.getMember().getId().equals(user.getId())) throw new NotYourUserRoomException();
        userRoomRepository.delete(groupChatMember);
    }

    @Override
    public UserRoomResponseDto findByUserRoomId(String userId, String userRoomId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        GroupChatMember groupChatMember = userRoomRepository.findById(userRoomId).orElseThrow(UserRoomNotFoundException::new);
        if(!groupChatMember.getMember().getId().equals(user.getId())) throw new NotYourUserRoomException();
        return UserRoomResponseDto.of(groupChatMember);
    }
}

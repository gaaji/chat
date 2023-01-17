package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.controller.dto.RoomSaveRequestDto;
import com.gaaji.chat.domain.ConnectionStatus;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.GroupChatMember;
import com.gaaji.chat.domain.chatroom.GroupChatRoom;
import com.gaaji.chat.execption.NotYourRoomException;
import com.gaaji.chat.execption.RoomNotFoundException;
import com.gaaji.chat.execption.UserNotFoundException;
import com.gaaji.chat.repository.RoomRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;

    @Override
    @Transactional
    public void patchUserConnectionStatus(String userId, String connectionStatus) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setConnectionStatus(ConnectionStatus.valueOf(connectionStatus));
    }

    @Override
    public RoomResponseDto findRoomByRoomId(String ownerId, String roomId) {
        User user = userRepository.findById(ownerId).orElseThrow(UserNotFoundException::new);
        ChatRoom chatRoom = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        userRoomRepository.findByMemberAndGroupChatRoom(user, (GroupChatRoom) chatRoom).orElseThrow(NotYourRoomException::new);
        return RoomResponseDto.of(chatRoom);
    }

    @Override
    @Transactional
    public RoomResponseDto saveRoomForUser(String userId, RoomSaveRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        ChatRoom chatRoom = roomRepository.save(ChatRoom.createGroupChatRoom(UUID.randomUUID().toString(), dto.getName()));
        userRoomRepository.save(GroupChatMember.create(UUID.randomUUID().toString(), user, chatRoom));
        for (RoomSaveRequestDto.UserDto member : dto.getMembers()) {
            User memberUser = userRepository.findById(member.getId()).orElseThrow(UserNotFoundException::new);
            userRoomRepository.save(GroupChatMember.create(UUID.randomUUID().toString(), memberUser, chatRoom));
        }
        return RoomResponseDto.of(chatRoom);
    }

    @Override
    public List<RoomResponseDto> findRoomsByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<RoomResponseDto> list = new ArrayList<>();
        for (GroupChatMember groupChatMember : user.getGroupChatMembers()) {
            list.add(RoomResponseDto.of(groupChatMember.getGroupChatRoom()));
        }
        return list;
    }
}

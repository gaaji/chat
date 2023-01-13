package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.domain.ConnectionStatus;
import com.gaaji.chat.domain.Room;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.execption.RoomNotFound;
import com.gaaji.chat.execption.UserNotFoundException;
import com.gaaji.chat.repository.RoomRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;

    @Override
    public List<RoomResponseDto> findAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return RoomResponseDto.listOf(rooms);
    }

    @Override
    @Transactional
    public void patchUserConnectionStatus(String userId, String connectionStatus) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setConnectionStatus(ConnectionStatus.valueOf(connectionStatus));
    }

    @Override
    public RoomResponseDto findRoomByRoomId(String roomId) {
        return RoomResponseDto.of(roomRepository.findById(roomId).orElseThrow(RoomNotFound::new));
    }
}

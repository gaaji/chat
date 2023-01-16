package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.UserRoomSaveRequestDto;
import com.gaaji.chat.controller.dto.UserRoomResponseDto;
import com.gaaji.chat.domain.Room;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.UserRoom;
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
        Room room = roomRepository.findById(dto.getId()).orElseThrow(RoomNotFoundException::new);
        UserRoom userRoom = userRoomRepository.save(UserRoom.create(UUID.randomUUID().toString(), user, room));
        return UserRoomResponseDto.of(userRoom);
    }

    @Override
    @Transactional
    public void delete(String userId, String userRoomId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserRoom userRoom = userRoomRepository.findById(userRoomId).orElseThrow(UserRoomNotFoundException::new);
        if(!userRoom.getUser().getId().equals(user.getId())) throw new NotYourUserRoomException();
        userRoomRepository.delete(userRoom);
    }

    @Override
    public UserRoomResponseDto findByUserRoomId(String userId, String userRoomId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserRoom userRoom = userRoomRepository.findById(userRoomId).orElseThrow(UserRoomNotFoundException::new);
        if(!userRoom.getUser().getId().equals(user.getId())) throw new NotYourUserRoomException();
        return UserRoomResponseDto.of(userRoom);
    }
}

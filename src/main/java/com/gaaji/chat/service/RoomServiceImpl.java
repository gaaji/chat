package com.gaaji.chat.service;

import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.controller.dto.RoomSaveRequestDto;
import com.gaaji.chat.domain.ConnectionStatus;
import com.gaaji.chat.domain.Room;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.UserRoom;
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
public class RoomServiceImpl implements RoomService {
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
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        userRoomRepository.findByUserAndRoom(user, room).orElseThrow(NotYourRoomException::new);
        return RoomResponseDto.of(room);
    }

    @Override
    @Transactional
    public RoomResponseDto saveRoomForUser(String userId, RoomSaveRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Room room = roomRepository.save(Room.create(UUID.randomUUID().toString(), dto.getName()));
        userRoomRepository.save(UserRoom.create(UUID.randomUUID().toString(), user, room));
        for (RoomSaveRequestDto.UserDto member : dto.getMembers()) {
            User memberUser = userRepository.findById(member.getId()).orElseThrow(UserNotFoundException::new);
            userRoomRepository.save(UserRoom.create(UUID.randomUUID().toString(), memberUser, room));
        }
        return RoomResponseDto.of(room);
    }

    @Override
    public List<RoomResponseDto> findRoomsByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<RoomResponseDto> list = new ArrayList<>();
        for (UserRoom userRoom : user.getUserRooms()) {
            list.add(RoomResponseDto.of(userRoom.getRoom()));
        }
        return list;
    }
}

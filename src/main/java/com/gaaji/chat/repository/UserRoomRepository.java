package com.gaaji.chat.repository;

import com.gaaji.chat.domain.Room;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<UserRoom, String> {
    Optional<UserRoom> findByUserAndRoom(User user, Room room);
    List<UserRoom> findByUser(User user);
}

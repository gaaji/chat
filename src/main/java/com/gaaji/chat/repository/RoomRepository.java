package com.gaaji.chat.repository;

import com.gaaji.chat.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, String> {
}

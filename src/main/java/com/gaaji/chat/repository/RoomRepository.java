package com.gaaji.chat.repository;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<ChatRoom, String> {
}

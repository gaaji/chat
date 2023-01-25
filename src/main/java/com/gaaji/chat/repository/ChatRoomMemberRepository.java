package com.gaaji.chat.repository;

import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, String> {
}
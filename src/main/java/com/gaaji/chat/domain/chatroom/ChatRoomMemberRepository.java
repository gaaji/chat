package com.gaaji.chat.domain.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, String> {
}
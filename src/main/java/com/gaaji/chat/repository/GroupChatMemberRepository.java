package com.gaaji.chat.repository;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupChatMemberRepository extends JpaRepository<ChatRoomMember, String> {
    Optional<ChatRoomMember> findByMemberAndChatRoom(User member, ChatRoom chatRoom);
    List<ChatRoomMember> findByMember(User member);
}

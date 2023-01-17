package com.gaaji.chat.repository;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.GroupChatMember;
import com.gaaji.chat.domain.chatroom.GroupChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<GroupChatMember, String> {
    Optional<GroupChatMember> findByMemberAndGroupChatRoom(User member, GroupChatRoom groupChatRoom);
    List<GroupChatMember> findByMember(User member);
}

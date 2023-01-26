package com.gaaji.chat.repository;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findByPost(Post post);
}

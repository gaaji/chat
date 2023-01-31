package com.gaaji.chat.repository;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    List<ChatRoom> findByPost(Post post);

    @Query("select cr from ChatRoom cr where cr.deletedAt is null")
    List<ChatRoom> findAll();

    @Query("select cr from ChatRoom cr")
    List<ChatRoom> findAllIncludeDeleted();

    @Query("select cr from ChatRoom as cr where cr.deletedAt is null and cr.id = :id")
    Optional<ChatRoom> findById(@Param("id") String id);

    @Query("select cr from ChatRoom as cr where cr.id = :id")
    Optional<ChatRoom> findByIdIncludeDeleted(@Param("id") String id);
}

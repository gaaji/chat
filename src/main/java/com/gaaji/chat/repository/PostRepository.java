package com.gaaji.chat.repository;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, String> {
}

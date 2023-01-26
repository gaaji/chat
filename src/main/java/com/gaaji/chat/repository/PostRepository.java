package com.gaaji.chat.repository;

import com.gaaji.chat.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {
}

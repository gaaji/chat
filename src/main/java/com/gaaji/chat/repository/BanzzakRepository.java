package com.gaaji.chat.repository;

import com.gaaji.chat.domain.post.Banzzak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanzzakRepository extends JpaRepository<Banzzak, String> {
}

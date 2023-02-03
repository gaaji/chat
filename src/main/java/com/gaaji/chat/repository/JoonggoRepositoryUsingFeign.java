package com.gaaji.chat.repository;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.post.Joonggo;

import java.util.Optional;

public interface JoonggoRepositoryUsingFeign {
    Optional<Joonggo> findById(String id) ;
}

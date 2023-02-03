package com.gaaji.chat.repository;

import com.gaaji.chat.domain.User;

import java.util.Optional;

public interface UserRepositoryUsingFeign {
    Optional<User> findById(String id) ;
}

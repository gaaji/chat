package com.gaaji.chat.service;

import com.gaaji.chat.domain.User;

public interface UserSearchUsingFeignService {
    User searchById(String id) ;
}

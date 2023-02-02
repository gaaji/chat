package com.gaaji.chat.controller;

import com.gaaji.chat.service.UserSearchUsingFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final UserSearchUsingFeignService userSearchUsingFeignService;

    @GetMapping("/user/{userId}/name")
    @ResponseStatus(HttpStatus.OK)
    public String getUserName(@PathVariable String userId) {
        return userSearchUsingFeignService.searchById(userId).getName();
    }
}

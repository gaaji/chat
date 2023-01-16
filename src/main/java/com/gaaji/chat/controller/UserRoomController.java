package com.gaaji.chat.controller;

import com.gaaji.chat.controller.dto.UserRoomResponseDto;
import com.gaaji.chat.controller.dto.UserRoomSaveRequestDto;
import com.gaaji.chat.service.UserRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-rooms")
public class UserRoomController {
    private final UserRoomService userRoomService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRoomResponseDto userRoomSave(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId, @RequestBody UserRoomSaveRequestDto dto) {
        return userRoomService.saveForJoining(userId, dto);
    }

    @GetMapping("/{userRoomId}")
    @ResponseStatus(HttpStatus.OK)
    public UserRoomResponseDto userRoom(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId, @PathVariable String userRoomId) {
        return userRoomService.findByUserRoomId(userId, userRoomId);
    }

    @DeleteMapping("/{userRoomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userRoomDelete(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId, @PathVariable String userRoomId) {
        userRoomService.delete(userId, userRoomId);
    }
}

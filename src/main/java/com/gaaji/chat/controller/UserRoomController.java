package com.gaaji.chat.controller;

import com.gaaji.chat.controller.dto.UserRoomResponseDto;
import com.gaaji.chat.controller.dto.UserRoomSaveRequestDto;
import com.gaaji.chat.service.GroupChatMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-rooms")
public class UserRoomController {
    private final GroupChatMemberService groupChatMemberService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRoomResponseDto userRoomSave(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId, @RequestBody @Validated UserRoomSaveRequestDto dto) {
        return groupChatMemberService.saveForJoining(userId, dto);
    }

    @GetMapping("/{userRoomId}")
    @ResponseStatus(HttpStatus.OK)
    public UserRoomResponseDto userRoom(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId, @PathVariable String userRoomId) {
        return groupChatMemberService.findByUserRoomId(userId, userRoomId);
    }

    @DeleteMapping("/{userRoomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userRoomDelete(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId, @PathVariable String userRoomId) {
        groupChatMemberService.delete(userId, userRoomId);
    }
}

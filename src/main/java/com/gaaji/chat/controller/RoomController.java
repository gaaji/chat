package com.gaaji.chat.controller;

import com.gaaji.chat.controller.dto.RoomResponseDto;
import com.gaaji.chat.controller.dto.RoomSaveRequestDto;
import com.gaaji.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // Save User's Room
    public RoomResponseDto myRoomSave(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId, @RequestBody RoomSaveRequestDto dto) {
        return chatRoomService.saveRoomForUser(userId, dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponseDto> myRoomList(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId) {
        return chatRoomService.findRoomsByUserId(userId);
    }

    @GetMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public RoomResponseDto myRoom(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId, @PathVariable String roomId) {
        return chatRoomService.findRoomByRoomId(userId, roomId);
    }
}

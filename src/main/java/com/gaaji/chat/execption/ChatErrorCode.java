package com.gaaji.chat.execption;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ChatErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "C-0001", "USER_NOT_FOUND"),

    GROUP_CHAT_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "C-0002", "GROUP_CHAT_MEMBER_NOT_FOUND"),

    NOT_YOUR_CHAT_ROOM(HttpStatus.FORBIDDEN, "C-0003", "NOT_YOUR_CHAT_ROOM"),

    NOT_YOUR_GROUP_CHAT_MEMBER(HttpStatus.FORBIDDEN, "C-0004", "NOT_YOUR_GROUP_CHAT_MEMBER"),

    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "C-0005", "CHAT_ROOM_NOT_FOUND");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}
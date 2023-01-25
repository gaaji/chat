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

    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "C-0005", "CHAT_ROOM_NOT_FOUND"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "c-0006", "NOT_FOUND"),
    POST_NOT_JOONGGO(HttpStatus.BAD_REQUEST, "c-0007", "the post of id is not a joonggo"),
    NOT_YOUR_RESOURCE(HttpStatus.FORBIDDEN, "c-0008", "not your resource"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "c-0009", "internal server error");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}
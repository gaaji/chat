package com.gaaji.chat.execption;

public class UserRoomNotFoundException extends AbstractApiException {
    public UserRoomNotFoundException() {
        super(ChatErrorCode.USER_ROOM_NOT_FOUND);
    }
}

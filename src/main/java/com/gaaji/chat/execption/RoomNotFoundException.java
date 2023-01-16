package com.gaaji.chat.execption;

public class RoomNotFoundException extends AbstractApiException {
    public RoomNotFoundException() {
        super(ChatErrorCode.ROOM_NOT_FOUND);
    }
}

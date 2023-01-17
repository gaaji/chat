package com.gaaji.chat.execption;

public class NotYourRoomException extends AbstractApiException {
    public NotYourRoomException() {
        super(ChatErrorCode.NOT_YOUR_ROOM);
    }
}

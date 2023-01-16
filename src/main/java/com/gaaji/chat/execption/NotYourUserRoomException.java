package com.gaaji.chat.execption;

public class NotYourUserRoomException extends AbstractApiException {
    public NotYourUserRoomException() {
        super(ChatErrorCode.NOT_YOUR_USER_ROOM);
    }
}

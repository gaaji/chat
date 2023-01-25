package com.gaaji.chat.execption;

public class NotYourUserRoomException extends AbstractApiException {
    public NotYourUserRoomException() {
        super(ChatErrorCode.NOT_YOUR_GROUP_CHAT_MEMBER);
    }
}

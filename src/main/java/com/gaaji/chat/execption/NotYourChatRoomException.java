package com.gaaji.chat.execption;

public class NotYourChatRoomException extends AbstractApiException {
    public NotYourChatRoomException() {
        super(ChatErrorCode.NOT_YOUR_CHAT_ROOM);
    }
}

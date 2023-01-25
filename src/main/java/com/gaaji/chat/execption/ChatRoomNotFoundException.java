package com.gaaji.chat.execption;

public class ChatRoomNotFoundException extends AbstractApiException {
    public ChatRoomNotFoundException() {
        super(ChatErrorCode.CHAT_ROOM_NOT_FOUND);
    }
}

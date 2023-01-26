package com.gaaji.chat.execption;

public class ChatRoomForTheBanzzakAlreadyExistsException extends AbstractApiException {
    public ChatRoomForTheBanzzakAlreadyExistsException() {
        super(ChatErrorCode.CHATROOM_FOR_THE_BANZZAK_ALREADY_EXISTS);
    }
}

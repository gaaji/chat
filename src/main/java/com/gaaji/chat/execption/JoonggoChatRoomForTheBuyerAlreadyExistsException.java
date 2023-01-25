package com.gaaji.chat.execption;

public class JoonggoChatRoomForTheBuyerAlreadyExistsException extends AbstractApiException {
    public JoonggoChatRoomForTheBuyerAlreadyExistsException() {
        super(ChatErrorCode.JOONGGO_CHAT_ROOM_FOR_THE_BUYER_ALREADY_EXISTS);
    }
}

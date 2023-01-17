package com.gaaji.chat.execption;

public class UserNotFoundException extends AbstractApiException {
    public UserNotFoundException() {
        super(ChatErrorCode.USER_NOT_FOUND);
    }
}

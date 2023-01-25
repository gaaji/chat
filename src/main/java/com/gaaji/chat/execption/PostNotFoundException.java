package com.gaaji.chat.execption;

public class PostNotFoundException extends AbstractApiException {
    public PostNotFoundException() {
        super(ChatErrorCode.NOT_FOUND);
    }
}

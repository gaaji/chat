package com.gaaji.chat.execption;

public class PostNotJoonggoException extends AbstractApiException {

    public PostNotJoonggoException() {
        super(ChatErrorCode.POST_NOT_JOONGGO);
    }
}

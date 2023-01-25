package com.gaaji.chat.execption;

public class InternalServerException extends AbstractApiException {
    public InternalServerException() {
        super(ChatErrorCode.INTERNAL_SERVER_ERROR);
    }
}

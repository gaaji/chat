package com.gaaji.chat.execption;

public class InterNalServerException extends AbstractApiException {
    public InterNalServerException() {
        super(ChatErrorCode.INTERNAL_SERVER_ERROR);
    }
}

package com.gaaji.chat.execption;

public class NotYourResourceException extends AbstractApiException {
    public NotYourResourceException() {
        super(ChatErrorCode.NOT_YOUR_RESOURCE);
    }
}

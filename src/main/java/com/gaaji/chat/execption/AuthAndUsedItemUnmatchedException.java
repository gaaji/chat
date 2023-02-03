package com.gaaji.chat.execption;

public class AuthAndUsedItemUnmatchedException extends AbstractApiException {
    public AuthAndUsedItemUnmatchedException() {
        super(ChatErrorCode.SERVICE_UNMATCHED);
    }
}

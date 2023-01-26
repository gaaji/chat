package com.gaaji.chat.execption;

public class PostNotBanzzakException extends AbstractApiException {
    public PostNotBanzzakException() {
        super(ChatErrorCode.POST_NOT_BANZZAK);
    }
}

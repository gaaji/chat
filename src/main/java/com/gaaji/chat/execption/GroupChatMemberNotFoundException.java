package com.gaaji.chat.execption;

public class GroupChatMemberNotFoundException extends AbstractApiException {
    public GroupChatMemberNotFoundException() {
        super(ChatErrorCode.GROUP_CHAT_MEMBER_NOT_FOUND);
    }
}

package com.gaaji.chat.domain.chatroom;

import com.gaaji.chat.domain.User;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
public class GroupChatMember {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private GroupChatRoom chatRoom; //숨기기 하면 null

    @ManyToOne(fetch = FetchType.LAZY)
    private User member;

    public static GroupChatMember create(String id, User user, ChatRoom chatRoom) {
        GroupChatMember groupChatMember = new GroupChatMember();
        groupChatMember.id = id;
        groupChatMember.chatRoom = (GroupChatRoom) chatRoom;
        groupChatMember.member = user;
        chatRoom.addUserRoom(groupChatMember);
        user.addUserRoom(groupChatMember);
        return groupChatMember;
    }

}

package com.gaaji.chat.domain.chatroom;

import com.gaaji.chat.domain.User;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
public class ChatRoomMember {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom; //숨기기 하면 null

    @ManyToOne(fetch = FetchType.LAZY)
    private User member;

    private String roomName = "";

    private boolean isLeft = false;

    public static ChatRoomMember create(User member, ChatRoom chatRoom, String roomName) {
        ChatRoomMember chatRoomMember = new ChatRoomMember();
        chatRoomMember.id = UUID.randomUUID().toString();
        chatRoomMember.chatRoom = chatRoom;
        chatRoomMember.member = member;
        chatRoomMember.roomName = roomName;
        chatRoom.addUser(chatRoomMember);
        member.addUserRoom(chatRoomMember);
        return chatRoomMember;
    }

    public void leave() {
        this.isLeft = true;
    }
}

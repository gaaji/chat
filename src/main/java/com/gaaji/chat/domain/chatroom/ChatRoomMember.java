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

    private String roomName;

    public static ChatRoomMember create(String id, User user, ChatRoom chatRoom) {
        ChatRoomMember chatRoomMember = new ChatRoomMember();
        chatRoomMember.id = id;
        chatRoomMember.chatRoom = chatRoom;
        chatRoomMember.member = user;
        chatRoom.addUser(chatRoomMember);
        user.addUserRoom(chatRoomMember);
        return chatRoomMember;
    }

    public static ChatRoomMember create(User user, ChatRoom chatRoom) {
        ChatRoomMember chatRoomMember = new ChatRoomMember();
        chatRoomMember.id = UUID.randomUUID().toString();
        chatRoomMember.chatRoom = chatRoom;
        chatRoomMember.member = user;
        chatRoom.addUser(chatRoomMember);
        user.addUserRoom(chatRoomMember);
        return chatRoomMember;
    }
}

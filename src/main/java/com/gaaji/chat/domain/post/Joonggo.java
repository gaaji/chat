package com.gaaji.chat.domain.post;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("joonggo")
public class Joonggo extends Post {
    @OneToMany(mappedBy = "post")
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @Override
    public void addChatRoom(ChatRoom chatRoom) {
        this.chatRooms.add(chatRoom);
    }

    public ChatRoom getChatRoomOf(User buyer) {
        for (ChatRoom chatRoom : chatRooms) {
            if(chatRoom.getMembers().contains(buyer)) return chatRoom;
        }
        return null;
    }
}

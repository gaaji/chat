package com.gaaji.chat.domain.post;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("banzzak")
public class Banzzak extends Post {
    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @Override
    public List<ChatRoom> getChatRooms() {
        ArrayList<ChatRoom> chatRooms = new ArrayList<>();
        if(chatRoom != null) chatRooms.add(chatRoom);
        return chatRooms;
    }

    @Override
    public void addChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void linkChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}

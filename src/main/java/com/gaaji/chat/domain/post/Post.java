package com.gaaji.chat.domain.post;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Post {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    public abstract List<ChatRoom> getChatRooms();
}

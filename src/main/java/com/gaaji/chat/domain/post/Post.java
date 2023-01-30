package com.gaaji.chat.domain.post;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Post {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    public static Joonggo randomJoonggoForTest(User seller) {
        Post joonggo = new Joonggo();
        joonggo.id = UUID.randomUUID().toString();
        joonggo.owner = seller;
        return (Joonggo) joonggo;
    }

    public static Banzzak randomBanzzakForTest(User owner, String name) {
        Post post = new Banzzak();
        post.id = UUID.randomUUID().toString();
        post.owner = owner;
        Banzzak banzzak = (Banzzak) post;
        banzzak.setNameForTest(name);
        return banzzak;
    }

    public abstract List<ChatRoom> getChatRooms();

    public abstract void addChatRoom(ChatRoom chatRoom);
}

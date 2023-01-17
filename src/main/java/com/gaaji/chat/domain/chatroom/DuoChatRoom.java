package com.gaaji.chat.domain.chatroom;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.post.Joonggo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("duo")
public class DuoChatRoom extends ChatRoom {
    @ManyToOne(fetch = FetchType.LAZY)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Joonggo joonggo;

    @Override
    public List<User> getMembers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(this.joonggo.getOwner());
        return users;
    }

    @Override
    public Joonggo getPost() {
        return this.joonggo;
    }
}

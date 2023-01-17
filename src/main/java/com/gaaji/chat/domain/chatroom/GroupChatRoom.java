package com.gaaji.chat.domain.chatroom;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.post.Banzzak;
import com.gaaji.chat.domain.post.Post;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("group")
public class GroupChatRoom extends ChatRoom{
    @OneToMany(mappedBy = "groupChatRoom")
    private List<GroupChatMember> groupChatMembers;

    @OneToOne(fetch = FetchType.LAZY)
    private Banzzak banzzak;

    @Override
    public List<User> getMembers() {
        return this.banzzak.getChatRoom().getMembers();
    }

    @Override
    public Post getPost() {
        return this.banzzak;
    }
}

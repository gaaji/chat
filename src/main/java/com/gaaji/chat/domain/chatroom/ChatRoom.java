package com.gaaji.chat.domain.chatroom;

import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.post.Post;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class ChatRoom {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "groupChatRoom")
    private List<GroupChatMember> groupChatMembers = new ArrayList<>();

    public static ChatRoom createGroupChatRoom(String id, String name) {
        ChatRoom chatRoom = new GroupChatRoom();
        chatRoom.id = id;
        chatRoom.name = name;
        return chatRoom;
    }

    public void addUserRoom(GroupChatMember groupChatMember) {
        this.groupChatMembers.add(groupChatMember);
    }

    public abstract List<User> getMembers();
    public abstract Post getPost();
}

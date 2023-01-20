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
public class ChatRoom {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Post post;

    public static ChatRoom createGroupChatRoom(String id, String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.id = id;
        chatRoom.name = name;
        return chatRoom;
    }

    public void addUser(ChatRoomMember chatRoomMember) {
        this.chatRoomMembers.add(chatRoomMember);
    }

    public List<User> getMembers() {
        List<User> members = new ArrayList<>();
        for (ChatRoomMember chatRoomMember : chatRoomMembers) {
            members.add(chatRoomMember.getMember());
        }
        return members;
    }

}

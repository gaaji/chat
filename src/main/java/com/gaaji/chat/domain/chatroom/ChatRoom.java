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
import java.util.UUID;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom {
    @Id
    private String id;

    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.PERSIST)
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public static ChatRoom createGroupChatRoom(String id) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.id = id;
        return chatRoom;
    }

    public static ChatRoom createDuoChatRoom(Post post) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.id = UUID.randomUUID().toString();
        chatRoom.post = post;
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

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", chatRoomMembers=" + chatRoomMembers +
                ", post=" + post +
                '}';
    }

    public void leaveMember(User memberToLeave) {
        for (ChatRoomMember chatRoomMember : chatRoomMembers)
            if (chatRoomMember.getMember().getId().equals(memberToLeave.getId())) chatRoomMember.leave();
    }
}

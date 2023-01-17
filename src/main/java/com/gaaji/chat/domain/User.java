package com.gaaji.chat.domain;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.GroupChatMember;
import com.gaaji.chat.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    private String id;

    @OneToMany(mappedBy = "member")
    private List<GroupChatMember> groupChatMembers = new ArrayList<>();

    @Convert(converter = ConnectionStatusConverter.class)
    private ConnectionStatus connectionStatus;

    @OneToMany(mappedBy = "owner")
    private List<Post> posts;

    @OneToMany
    private List<ChatRoom> chatRooms;

    public User(String id, ConnectionStatus connectionStatus) {
        this.id = id;
        this.connectionStatus = connectionStatus;
    }

    public void addUserRoom(GroupChatMember groupChatMember) {
        this.groupChatMembers.add(groupChatMember);
    }

    public void online() {
        this.connectionStatus = ConnectionStatus.ONLINE;
    }

    public void offline() {
        this.connectionStatus = ConnectionStatus.OFFLINE;
    }

    public boolean isOnline() {
        return this.connectionStatus == ConnectionStatus.ONLINE;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}

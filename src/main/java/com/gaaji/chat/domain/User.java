package com.gaaji.chat.domain;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
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
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();

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

    public void addUserRoom(ChatRoomMember chatRoomMember) {
        this.chatRoomMembers.add(chatRoomMember);
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

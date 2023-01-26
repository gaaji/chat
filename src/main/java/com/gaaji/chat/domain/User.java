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

    @OneToMany(mappedBy = "owner")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<ChatRoom> chatRooms;

    public User(String id) {
        this.id = id;
    }

    public void addUserRoom(ChatRoomMember chatRoomMember) {
        this.chatRoomMembers.add(chatRoomMember);
    }

}

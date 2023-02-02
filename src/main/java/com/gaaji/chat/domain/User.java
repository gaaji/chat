package com.gaaji.chat.domain;

import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import com.gaaji.chat.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Post> posts;

    public User(String id) {
        this.id = id;
    }

    public static User create(String id, String name) {
        User user = new User();
        user.id = id;
        user.name = name;
        return user;
    }

    public void addUserRoom(ChatRoomMember chatRoomMember) {
        this.chatRoomMembers.add(chatRoomMember);
    }

    public List<ChatRoom> getChatRooms() {
        return chatRoomMembers.stream().map(chatRoomMember -> chatRoomMember.getChatRoom()).collect(Collectors.toList());
    }

}

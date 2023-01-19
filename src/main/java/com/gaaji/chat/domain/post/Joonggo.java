package com.gaaji.chat.domain.post;

import com.gaaji.chat.domain.chatroom.DuoChatRoom;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("joonggo")
public class Joonggo extends Post {
    @OneToMany(mappedBy = "joonggo")
    private List<DuoChatRoom> chatRooms;
}

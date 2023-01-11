package com.gaaji.chat.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
public class UserRoom {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room; //숨기기 하면 null

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public static UserRoom create(User user, Room room) {
        UserRoom userRoom = new UserRoom();
        userRoom.room = room;
        userRoom.user = user;
        room.addUserRoom(userRoom);
        user.addUserRoom(userRoom);
        return userRoom;
    }

}

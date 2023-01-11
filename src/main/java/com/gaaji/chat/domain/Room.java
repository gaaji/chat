package com.gaaji.chat.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Room {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "room")
    private List<UserRoom> userRooms;

    @OneToMany
    private List<Message> messages;

    public static Room create(String id, String name) {
        Room room = new Room();
        room.id = id;
        room.name = name;
        return room;
    }

    public void addUserRoom(UserRoom userRoom) {
        this.userRooms.add(userRoom);
    }
}

package com.gaaji.chat.domain;

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
public class Room {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "room")
    private List<UserRoom> userRooms = new ArrayList<>();

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

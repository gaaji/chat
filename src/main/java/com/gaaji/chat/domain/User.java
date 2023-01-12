package com.gaaji.chat.domain;

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

    @OneToMany(mappedBy = "user")
    private List<UserRoom> userRooms = new ArrayList<>();

    public User(String id, ConnectionStatus connectionStatus) {
        this.id = id;
        this.connectionStatus = connectionStatus;
    }

    public void addUserRoom(UserRoom userRoom) {
        this.userRooms.add(userRoom);
    }

    @Convert(converter = ConnectionStatusConverter.class)
    private ConnectionStatus connectionStatus;

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

package com.gaaji.chat.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User {
    @Id
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<UserRoom> userRooms;
}

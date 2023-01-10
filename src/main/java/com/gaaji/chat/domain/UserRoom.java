package com.gaaji.chat.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserRoom {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room; //숨기기 하면 null

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}

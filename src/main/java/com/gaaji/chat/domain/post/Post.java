package com.gaaji.chat.domain.post;

import com.gaaji.chat.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Post {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
}

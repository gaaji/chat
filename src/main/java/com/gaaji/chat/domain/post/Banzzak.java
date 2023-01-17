package com.gaaji.chat.domain.post;

import com.gaaji.chat.domain.chatroom.GroupChatRoom;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("banzzak")
public class Banzzak extends Post {
    @OneToOne(mappedBy = "banzzak", fetch = FetchType.LAZY)
    private GroupChatRoom chatRoom;
}

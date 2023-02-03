package com.gaaji.chat.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "events")
@Getter
@ConstructorBinding
@RequiredArgsConstructor
public class EventProps {
    private final PostEventProps post;
    private final ChatEventProps chat;

    @Getter
    @RequiredArgsConstructor
    public static final class PostEventProps {
        private final BanzzakEventProps banzzak;

        @Getter
        @RequiredArgsConstructor
        public static class BanzzakEventProps {
            private final String created;
            private final String deleted;
            private final String userJoined;
            private final String userLeft;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static final class ChatEventProps {
        private final ChatRoomEventProps chatRoom;
        private final MemberEventProps member;

        @Getter
        @RequiredArgsConstructor
        public static final class ChatRoomEventProps {
            private final String created;
            private final String deleted;
        }

        @Getter
        @RequiredArgsConstructor
        public static final class MemberEventProps {
            private final String added;
            private final String left;
        }
    }
}

package com.gaaji.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import com.gaaji.chat.domain.post.Banzzak;
import com.gaaji.chat.domain.post.Post;
import com.gaaji.chat.execption.ChatRoomForTheBanzzakAlreadyExistsException;
import com.gaaji.chat.execption.PostNotBanzzakException;
import com.gaaji.chat.execption.PostNotFoundException;
import com.gaaji.chat.repository.ChatRoomRepository;
import com.gaaji.chat.repository.PostRepository;
import com.gaaji.chat.service.dto.BanzzakCreatedEventDto;
import com.gaaji.chat.service.dto.ChatRoomCreatedEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GroupChatServiceImpl implements GroupChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final PostRepository postRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public void handleBanzzakCreated(String body) throws JsonProcessingException {
        log.info("Event Caught: post-banzzakCreated");
        BanzzakCreatedEventDto banzzakCreatedEventDto = new ObjectMapper().readValue(body, BanzzakCreatedEventDto.class);
        Post post = postRepository.findById(banzzakCreatedEventDto.getPostId()).orElseThrow(PostNotFoundException::new);
        if(! (post instanceof Banzzak)) throw new PostNotBanzzakException();
        if(post.getChatRooms().size() > 0) throw new ChatRoomForTheBanzzakAlreadyExistsException();

        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.createChatRoom());
        chatRoom.linkPost(post);
        ChatRoomMember.create(post.getOwner(), chatRoom);

        kafkaTemplate.send("chat-chatRoomCreated", new ObjectMapper().writeValueAsString(ChatRoomCreatedEventDto.create(chatRoom)));
        log.info("Event Occurred: chat-chatRoomCreated");
    }
}

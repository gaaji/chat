package com.gaaji.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import com.gaaji.chat.domain.post.Banzzak;
import com.gaaji.chat.domain.post.Post;
import com.gaaji.chat.execption.*;
import com.gaaji.chat.repository.BanzzakRepository;
import com.gaaji.chat.repository.ChatRoomRepository;
import com.gaaji.chat.repository.PostRepository;
import com.gaaji.chat.repository.UserRepository;
import com.gaaji.chat.service.dto.BanzzakCreatedEventDto;
import com.gaaji.chat.service.dto.BanzzakUserJoinedEventDto;
import com.gaaji.chat.service.dto.ChatRoomCreatedEventDto;
import com.gaaji.chat.service.dto.MemberAddedEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GroupChatServiceImpl implements GroupChatService {
    private final BanzzakRepository banzzakRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PostRepository postRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    @KafkaListener(topics = "post-banzzakCreated", errorHandler = "kafkaErrorHandler")
    public void handleBanzzakCreated(String body) throws JsonProcessingException {
        log.info("Event Caught: post-banzzakCreated " + body);
        ObjectMapper objectMapper = new ObjectMapper();
        BanzzakCreatedEventDto banzzakCreatedEventDto = objectMapper.readValue(body, BanzzakCreatedEventDto.class);
        Post post = postRepository.findById(banzzakCreatedEventDto.getId()).orElseThrow(PostNotFoundException::new);
        if(! (post instanceof Banzzak)) throw new PostNotBanzzakException();
        if(post.getChatRooms().size() > 0) throw new ChatRoomForTheBanzzakAlreadyExistsException();

        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.createChatRoom());
        chatRoom.relatePost(post);
        ChatRoomMember.create(post.getOwner(), chatRoom, ((Banzzak) post).getName());

        String data = objectMapper.writeValueAsString(ChatRoomCreatedEventDto.create(chatRoom));
        kafkaTemplate.send("chat-chatRoomCreated", data);
        log.info("Event Occurred: chat-chatRoomCreated " + data);
    }

    @Override
    @Transactional
    @KafkaListener(topics = "post-banzzakUserJoined", errorHandler = "kafkaErrorHandler")
    public void handleBanzzakUserJoined(String body) throws JsonProcessingException {
        log.info("Event Caught: post-banzzakUserJoined " + body);
        ObjectMapper objectMapper = new ObjectMapper();
        BanzzakUserJoinedEventDto banzzakUserJoinedEventDto = objectMapper.readValue(body, BanzzakUserJoinedEventDto.class);
        User user = userRepository.findById(banzzakUserJoinedEventDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(banzzakUserJoinedEventDto.getPostId()).orElseThrow(PostNotBanzzakException::new);
        if(!(post instanceof Banzzak)) throw new PostNotBanzzakException();

        Banzzak banzzak = (Banzzak) post;
        ChatRoom chatRoom = banzzak.getChatRoom();
        if(chatRoom == null) throw new InternalServerException(); // 이미 있어야 정상이다.
        if(chatRoom.getMembers().contains(user)) return;

        ChatRoomMember.create(user, chatRoom, banzzak.getName()); // ChatRoom 에 User 추가

        String data = objectMapper.writeValueAsString(MemberAddedEventDto.create(chatRoom.getId(), user.getId()));
        kafkaTemplate.send("chat-memberAdded", data);
        log.info("Event Occurred: chat-memberAdded " + data);
    }
}

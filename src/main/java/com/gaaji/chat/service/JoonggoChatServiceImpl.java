package com.gaaji.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chat.controller.dto.JoonggoChatRoomSaveRequestDto;
import com.gaaji.chat.controller.dto.ChatRoomResponseDto;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import com.gaaji.chat.domain.post.Joonggo;
import com.gaaji.chat.domain.post.Post;
import com.gaaji.chat.execption.*;
import com.gaaji.chat.repository.ChatRoomRepository;
import com.gaaji.chat.repository.PostRepository;
import com.gaaji.chat.service.dto.ChatRoomCreatedEventDto;
import com.gaaji.chat.service.dto.ChatRoomDeletedEventDto;
import com.gaaji.chat.service.dto.MemberLeftEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class JoonggoChatServiceImpl implements JoonggoChatService {
    private final PostRepository postRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserSearchUsingFeignService userSearchUsingFeignService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public ChatRoomResponseDto createDuoChatRoom(String authId, JoonggoChatRoomSaveRequestDto dto) {
        if(!authId.equals(dto.getBuyerId())) throw new NotYourResourceException();
        User buyer = userSearchUsingFeignService.searchById(dto.getBuyerId());
        Post post = postRepository.findById(dto.getJoonggoId()).orElseThrow(PostNotFoundException::new);
        if(!(post instanceof Joonggo)) throw new PostNotJoonggoException();
        if(((Joonggo) post).getChatRoomOf(buyer) != null) throw new JoonggoChatRoomForTheBuyerAlreadyExistsException();

        ChatRoom duoChatRoom = chatRoomRepository.save(ChatRoom.createChatRoom());
        duoChatRoom.relatePost(post);
        ChatRoomMember.create(post.getOwner(), duoChatRoom, buyer.getName());
        ChatRoomMember.create(buyer, duoChatRoom, post.getOwner().getName());

        try {
            String body = new ObjectMapper().writeValueAsString(ChatRoomCreatedEventDto.create(duoChatRoom));
            kafkaTemplate.send("chat-chatRoomCreated", body);
            log.info("Event Occurred: chat-chatRoomCreated");
        } catch (JsonProcessingException e) {
            throw new InternalServerException();
        }
        return ChatRoomResponseDto.of(duoChatRoom, post.getOwner().getName());
    }

    @Override
    @Transactional
    public void leaveDuoChatRoom(String authId, String chatRoomId, String memberIdToLeave) {
        if(!authId.equals(memberIdToLeave)) throw new NotYourResourceException();
        User memberToLeave = userSearchUsingFeignService.searchById(memberIdToLeave);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        chatRoom.leaveMember(memberToLeave);
        try {
            String body = new ObjectMapper().writeValueAsString(MemberLeftEventDto.create(memberToLeave));
            kafkaTemplate.send("chat-memberLeft", body);
            log.info("Event Occurred: chat-memberLeft " + body);
        } catch (JsonProcessingException e) {
            throw new InternalServerException();
        }

        if (chatRoom.getChatRoomMembers().stream().filter(chatRoomMember -> !chatRoomMember.isLeft()).count() > 0) return;
        try {
            String body = new ObjectMapper().writeValueAsString(ChatRoomDeletedEventDto.create(chatRoom));
            kafkaTemplate.send("chat-chatRoomDeleted", body);
            log.info("Event Occurred: chat-chatRoomDeleted " + body);
        } catch (JsonProcessingException e) {
            throw new InternalServerException();
        }
    }
}

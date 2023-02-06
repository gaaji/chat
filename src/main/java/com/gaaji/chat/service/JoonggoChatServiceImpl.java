package com.gaaji.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chat.config.EventProps;
import com.gaaji.chat.controller.dto.JoonggoChatRoomSaveRequestDto;
import com.gaaji.chat.controller.dto.ChatRoomResponseDto;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.chatroom.ChatRoom;
import com.gaaji.chat.domain.chatroom.ChatRoomMember;
import com.gaaji.chat.domain.post.Joonggo;
import com.gaaji.chat.domain.post.Post;
import com.gaaji.chat.execption.*;
import com.gaaji.chat.repository.*;
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
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepositoryUsingFeign userRepositoryUsingFeign;
    private final JoonggoRepositoryUsingFeign joonggoRepositoryUsingFeign;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final EventProps eventProps;

    @Override
    @Transactional
    public ChatRoomResponseDto createDuoChatRoom(String authId, JoonggoChatRoomSaveRequestDto dto) {
        if(!authId.equals(dto.getBuyerId())) throw new NotYourResourceException();
        User buyer = userRepositoryUsingFeign.findById(dto.getBuyerId()).orElseThrow(UserNotFoundException::new);
        Joonggo joonggo = joonggoRepositoryUsingFeign.findById(dto.getJoonggoId()).orElseThrow(PostNotFoundException::new);
        if(joonggo.getChatRoomOf(buyer) != null) throw new JoonggoChatRoomForTheBuyerAlreadyExistsException();

        ChatRoom duoChatRoom = chatRoomRepository.save(ChatRoom.createChatRoom());
        duoChatRoom.relatePost(joonggo);
        ChatRoomMember.create(joonggo.getOwner(), duoChatRoom, buyer.getName());
        ChatRoomMember.create(buyer, duoChatRoom, joonggo.getOwner().getName());

        try {
            String body = new ObjectMapper().writeValueAsString(ChatRoomCreatedEventDto.create(duoChatRoom));
            String eventName = eventProps.getChat().getChatRoom().getCreated();
            kafkaTemplate.send(eventName, body);
            log.info("Event Occurred: " + eventName);
        } catch (JsonProcessingException e) {
            throw new InternalServerException();
        }
        return ChatRoomResponseDto.of(duoChatRoom, joonggo.getOwner().getName());
    }

    @Override
    @Transactional
    public void leaveDuoChatRoom(String authId, String chatRoomId, String memberIdToLeave) {
        if(!authId.equals(memberIdToLeave)) throw new NotYourResourceException();
        User memberToLeave = userRepositoryUsingFeign.findById(memberIdToLeave).orElseThrow(UserNotFoundException::new);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        chatRoom.leaveMember(memberToLeave);
        try {
            String body = new ObjectMapper().writeValueAsString(MemberLeftEventDto.create(memberToLeave));
            String eventName = eventProps.getChat().getMember().getLeft();
            kafkaTemplate.send(eventName, body);
            log.info("Event Occurred: {} {}", eventName, body);
        } catch (JsonProcessingException e) {
            throw new InternalServerException();
        }

        if (chatRoom.getChatRoomMembers().stream().filter(chatRoomMember -> !chatRoomMember.isLeft()).count() > 0) return;
        try {
            String body = new ObjectMapper().writeValueAsString(ChatRoomDeletedEventDto.create(chatRoom));
            String eventName = eventProps.getChat().getChatRoom().getDeleted();
            kafkaTemplate.send(eventName, body);
            log.info("Event Occurred: {} {}", eventName, body);
        } catch (JsonProcessingException e) {
            throw new InternalServerException();
        }
    }
}

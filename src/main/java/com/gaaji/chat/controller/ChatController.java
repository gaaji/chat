package com.gaaji.chat.controller;

import com.gaaji.chat.controller.dto.ChatDto;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;

    /**
     * 유저 채팅방 입장을 알리고,
     * 서버는 클라이언트 세션에 유저 아이디와 room 아이디를
     * put 해주는 메소드
     */
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
        // ChatDto의 Sender를 유저로 매핑? 아님 유저 ID로만 세팅?
        User user = chatService.findUserById(chat.getSenderId());

        headerAccessor.getSessionAttributes().put("userId", user.getId());
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        // socket token 발행

        // 유저의 상태를 온라인으로?
        user.online();

        // 하위 코드는 우리에게 필요없는 코드
//        chat.setMessage(chat.getSender() + " 님 입장!!");
//        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chat) {

        // socket token 검증


        chat.setMessage(chat.getMessage());
        template.convertAndSend("/sub/chat/room"+chat.getRoomId(), chat);
    }

    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent disconnectEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(disconnectEvent.getMessage());

        // 클라이언트 세션에서 user id와 room id을 가져옴
        String userId = (String) headerAccessor.getSessionAttributes().get("userId");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        // 유저를 오프라인으로?
        User user = chatService.findUserById(userId);
        user.offline();

        // 하위 코드는 우리에게 필요없는 코드
//        if (username != null) {
//
//            // builder 어노테이션 활용
//            ChatDTO chat = ChatDTO.builder()
//                    .type(ChatDTO.MessageType.LEAVE)
//                    .sender(username)
//                    .message(username + " 님 퇴장!!")
//                    .build();
//
//            template.convertAndSend("/sub/chat/room/" + roomId, chat);
//        }
    }


}

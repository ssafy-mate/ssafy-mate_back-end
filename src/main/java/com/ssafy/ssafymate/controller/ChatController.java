package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.dto.ChatDto.ChatMessageDto;
import com.ssafy.ssafymate.service.ChattingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * WebSocketConfig에서
 *
 *	"/app"로 시작하는 대상이 있는 클라이언트에서 보낸 모든 메시지는 @MessageMapping 어노테이션이 달린
 *
 *	메서드로 라우팅 됩니다.
 *
 *	예를 들어
 *
 *	"/app/chat.sendMessage" 인 메세지는 sendMessage()로 라우팅 되며
 *
 *	"/app/chat.addUser" 인 메시지는 addUser()로 라우팅됩니다.
 *
 *	event listener를 이용하여 소켓 연결(socket connect) 그리고 소켓 연결 끊기(disconnect) 이벤트를 수신하여
 *
 *	사용자가 채팅방을 참여(JOIN)하거나 떠날때(LEAVE)의 이벤트를 logging 하거나 broadcast 할 수 있습니다.
 */

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    @Autowired
    private ChattingService chattingService;

    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/queue/public/{roomId}")
    public ChatMessageDto sendMessage(@Payload ChatMessageDto chatMessage) {
        chattingService.saveHistory(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser/{roomId}")
    @SendTo("/queue/public/{roomId}")
    public ChatMessageDto addUser(@Payload ChatMessageDto chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("senderId", chatMessage.getSenderId());
        return chatMessage;
    }
}

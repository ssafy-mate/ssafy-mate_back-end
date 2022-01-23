//package com.ssafy.ssafymate.controller;
//
//import com.ssafy.ssafymate.dto.ChatDto.ChatMessageDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@RequiredArgsConstructor
//public class StompChatController {
//
//    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
//
//    // Client가 SEND할 수 있는 경로
//    // stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
//    // "/app/chat/enter"
//    @MessageMapping(value = "/chat/enter")
//    public void enter(ChatMessageDto message){
//        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
//        template.convertAndSend("/queue/chat/room/" + message.getRoomId(), message);
//    }
//
//    @MessageMapping(value = "/chat/message")
//    public void message(ChatMessageDto message){
//        template.convertAndSend("/queue/chat/room/" + message.getRoomId(), message);
//    }
//}
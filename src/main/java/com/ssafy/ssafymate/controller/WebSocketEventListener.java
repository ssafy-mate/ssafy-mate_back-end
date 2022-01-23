package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.dto.ChatDto.ChatMessageDto;
import com.ssafy.ssafymate.dto.ChatDto.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;


	// 연결
	@EventListener
	public void handlerWebSocketConnectListener(SessionConnectedEvent event) {
		logger.info("Received a new web socket connection");
	}
	
/*
 * 메소드 이름에 STOMP라는 단어가 있을 수 있습니다. 이러한 메서드는 Spring 프레임 워크 STOMP 구현에서 제공됩니다.
 * STOMP는 Simple Text Oriented Messaging Protocol의 약어입니다.
 * 데이터 교환의 형식과 규칙을 정의하는 메시징 프로토콜입니다.
 * STOM를 사용하는 이유?
 * WebSocket은 통신 프로토콜 일뿐입니다.
 * 특정 주제를 구독한 사용자에게만 메시지를 보내는 방법 또는 특정 사용자에게 메시지를 보내는 방법과 같은 내용은 정의하지 않습니다.
 * 이러한 기능을 위해서는 STOMP가 필요합니다.
 */

	// 연결 해제
	@EventListener
	public void handlerWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		
		Long senderId = (Long) headerAccessor.getSessionAttributes().get("senderId");
		String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
		if(senderId != null) {
			logger.info("User Disconnected : " + senderId);
			
			ChatMessageDto chatMessageDto = ChatMessageDto.builder()
					.type(MessageType.LEAVE)
					.senderId(senderId)
					.build();
			
			messagingTemplate.convertAndSend("/queue/public/"+roomId, chatMessageDto);

		}
	}
}

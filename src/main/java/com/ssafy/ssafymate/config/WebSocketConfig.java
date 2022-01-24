package com.ssafy.ssafymate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // WebSocker 서버 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*
     * 클라이언트가 웹 소켓 서버에 연결하는 데 사용할 웹 소켓 앤드 포인트를 등록함.
     * 엔드 포인트 구성에 withSockJS() 를 사용
     * SockJS는 웹 소켓을 지원하지 않는 브라우저에 폴백 옵션을 활성화하는 데 사용됨.
     * Fallback 이란?  어떤 기능이 약해지거나 제대로 동작하지 않을 때, 이에 대처하는 기능 또는 동작
     * ws는 WebSocket 클라이언트가 Handshake를 위해 연결해야하는 end-point의 url이다
       let socket = new SockJS('http://localhost:8080/ws')
       this.stompClient = Stomp.over(socket)
      registerStompEndpoints 메서드는 클라이언트가 서버에 연결하는 데 사용할 웹 소켓 끝점을 등록하는 데 사용됩니다.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // setAllowedOrigins("*")
        registry.addEndpoint("/ws").withSockJS();
    }

    //configureMessageBroker 메소드는 한 클라이언트에서 다른 클라이언트로 메시지를 라우팅하는 데
    // 사용되는 메시지 브로커를 구성하는 데 사용됩니다.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // destination이 /app으로 된 메세지를 @Controller의 @MessageMapping으로 라우팅한다
        // 서버에서 클라이언트로부터 메세지를 받을 api의 prefix를 반환한다.
        registry.setApplicationDestinationPrefixes("/app");	// publish

        // 메모리 기반 메세지 브로커가 해당 api를 구독하고 있는 클라이언트에게 메세지를 전달한다.
        registry.enableSimpleBroker("/queue"); // subscibe
    }
}
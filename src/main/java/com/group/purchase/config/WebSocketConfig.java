package com.group.purchase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration  //애플리케이션의 환경 설정 클래스
@EnableWebSocketMessageBroker   //웹소켓 기반의 메시지 브로커 기능을 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                //서버와 연결을 맺을 구체적인 주소(엔드포인트) -> 클라이언트 측에서는 이 주소를 향해 최초의 웹소켓 연결을 요청
                .setAllowedOriginPatterns("*");
                //CORS(교차 출처 리소스 공유)를 설정
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        //메시지 수신(구독, Subscribe) 경로 설정: 스프링에 내장된 단순한(Simple) 인메모리 메시지 브로커를 활성화
        //해당 주소를 구독하고 있는 모든 클라이언트에게 동시에 뿌림
        registry.setApplicationDestinationPrefixes("/app");
        //메시지 발신(발행, Publish) 경로 설정
    }
}
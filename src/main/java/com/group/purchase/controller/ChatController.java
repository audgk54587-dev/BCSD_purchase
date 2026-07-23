package com.group.purchase.controller;

import com.group.purchase.dto.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    // STOMP 메시지 전송을 위한 스프링 제공 템플릿
    private final SimpMessagingTemplate messagingTemplate;

    // 포스트맨에서 발송 테스트를 하기 위한 REST API 엔드포인트
    @PostMapping("/send")
    public ResponseEntity<String> sendChatMessage(@RequestBody ChatMessageRequest request) {

        // 콘솔에 메시지 내용 출력
        System.out.println("========================================");
        System.out.println("📩 [새로운 채팅 수신]");
        System.out.println("방 번호(Room ID) : " + request.getRoomId());
        System.out.println("발신자(Sender)   : " + request.getSender());
        System.out.println("메시지(Message)  : " + request.getMessage());
        System.out.println("========================================");

        // 채팅방으로 메시지 브로드캐스팅 (실제 클라이언트 연결 시 작동)
        messagingTemplate.convertAndSend("/topic/chat/room/" + request.getRoomId(), request);
        //자바 객체(DTO)를 네트워크로 전송하기 쉬운 형태(주로 JSON 형식)로 알아서 변환 후 전송

        return ResponseEntity.ok(request.getRoomId() + "번 방으로 메시지가 전송되었습니다.");
        //ResponseEntity.ok: 스프링(Spring) 프레임워크에서 HTTP 응답(Response)을 세밀하게 제어
    }
}
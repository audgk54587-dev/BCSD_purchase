package com.group.purchase.controller;

import com.group.purchase.dto.DirectMessageRequest;
import com.group.purchase.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody DirectMessageRequest request) {
        messageService.sendMessage(request);
        return ResponseEntity.ok("참가자에게 문자가 성공적으로 전송되었습니다.");
    }
}
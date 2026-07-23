package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class ChatMessageRequest {
    private String roomId;   // 채팅방 번호
    private String sender;   // 보내는 사람 이메일 또는 이름
    private String message;  // 채팅 내용
}
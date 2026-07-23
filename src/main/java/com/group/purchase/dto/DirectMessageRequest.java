package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class DirectMessageRequest {
    private Long boardId;         // 게시글 번호
    private String senderEmail;   // 작성자 이메일
    private String receiverEmail; // 참가자 이메일
}
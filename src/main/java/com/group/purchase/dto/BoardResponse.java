//데이터베이스에서 꺼내온 게시글(엔티티) 정보를 클라이언트(프론트엔드) 화면에 보여주기 알맞은 형태로 가공하여 반환
package com.group.purchase.dto;

import com.group.purchase.domain.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private String authorName; // 이메일 대신 작성자 이름 반환
    private LocalDateTime createdAt;

    // Board 엔티티를 받아서 DTO로 변환하는 생성자
    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.authorName = board.getAuthor().getName();
        this.createdAt = board.getCreatedAt();
    }
}
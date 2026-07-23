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
    private int currentParticipants; // 현재 인원
    private int targetParticipants;  // 모집 인원
    private int totalQuantity;       // 총 구매 개수
    private int unitPrice;           // 단가
    private int pricePerPerson;      // 1인당 결제 금액 (구매량에 따른 가격)
    private String accountNumber;    // 방장 계좌번호
    private String deadline;         // 입금 기한

    // Board 엔티티를 받아서 DTO로 변환하는 생성자
    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.authorName = board.getAuthor().getName();
        this.createdAt = board.getCreatedAt();
        this.currentParticipants = board.getCurrentParticipants();
        this.targetParticipants = board.getTargetParticipants();
        this.totalQuantity = board.getTotalQuantity();
        this.unitPrice = board.getUnitPrice();
        this.deadline = board.getDeadline();
        this.accountNumber = board.getAuthor().getAccountNumber();

        // 1인당 결제 금액 자동 계산 로직
        if (this.targetParticipants > 0) {
            int quantityPerPerson = this.totalQuantity / this.targetParticipants; // 1인당 분배 개수
            this.pricePerPerson = quantityPerPerson * this.unitPrice;             // 최종 결제 금액
        } else {
            this.pricePerPerson = 0;
        }
    }

}
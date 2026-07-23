package com.group.purchase.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor  //파라미터가 없는 기본 생성자를 자동으로 만듦
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //기본 키 값을 데이터베이스의 자동 증가 기능에 위임
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;
    // 작성 시간

    @ManyToOne(fetch = FetchType.LAZY)
    //다대일(N:1) 관계
    //fetch = FetchType.LAZY(지연로딩): 정보가 많이 가져오기 때문에 속도가 느려지는걸 방지
    @JoinColumn(name = "member_id")
    //데이터베이스의 board 테이블에 이 관계를 기록할 외래 키(Foreign Key) 컬럼의 이름을 member_id로 지정
    private Member author;

    private int targetParticipants; // 목표 모집 인원
    private int currentParticipants; // 현재 참여 인원
    private int totalQuantity; // 총 구매 개수
    private int unitPrice;     // 단가
    private String deadline;   // 입금 기한

    @Builder
    public Board(String title, String content, Member author, int targetParticipants,
                 int totalQuantity, int unitPrice, String deadline) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.targetParticipants = targetParticipants;
        this.currentParticipants = 0;   //초기화
        this.totalQuantity = totalQuantity;
        this.unitPrice = unitPrice;
        this.deadline = deadline;
        this.createdAt = LocalDateTime.now();
    }

    // 인원 증가 로직
    public void increaseParticipants() {
        if (this.currentParticipants >= this.targetParticipants) {
            throw new IllegalArgumentException("모집 인원이 모두 찼습니다.");
        }
        this.currentParticipants++;
    }

    // 인원 감소 로직
    public void decreaseParticipants() {
        if (this.currentParticipants <= 0) {
            throw new IllegalArgumentException("참여 인원이 0명 미만이 될 수 없습니다.");
        }
        this.currentParticipants--;
    }
}
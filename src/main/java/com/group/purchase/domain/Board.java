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
    //fetch = FetchType.LAZY: 회원 정보가 진짜 필요한 순간에 추가로 가져오도록
    @JoinColumn(name = "member_id")
    //데이터베이스의 board 테이블에 이 관계를 기록할 외래 키(Foreign Key) 컬럼의 이름을 member_id로 지정
    private Member author;

    @Builder
    public Board(String title, String content, Member author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }
}
package com.group.purchase.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)      //파라미터가 없는 기본 생성자
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //데이터베이스에 데이터가 들어갈 때 자동으로 1, 2, 3... 순서대로 증가
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String accountNumber;

    @Builder
    public Member(String email, String password, String name, String phoneNumber, String accountNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateInfo(String name, String phoneNumber, String accountNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
    }
}

package com.group.purchase.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

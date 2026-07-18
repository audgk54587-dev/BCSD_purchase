//회원가입 시 필요한 모든 정보(이메일, 비밀번호, 이름, 연락처, 계좌번호 등)를 한 번에 담아 서버로 보내는 객체
package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String accountNumber;
}

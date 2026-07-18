//이메일로 받은 인증 코드가 맞는지 검증하기 위해 이메일 주소와 인증 번호를 함께 담아 전달하는 객체
package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class VerifyRequest {
    private String email;
    private String code;
}
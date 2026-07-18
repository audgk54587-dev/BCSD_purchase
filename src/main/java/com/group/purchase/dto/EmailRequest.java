//이메일 인증 번호 발송을 요청할 때 사용자의 이메일 주소를 담는 객체
package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class EmailRequest {
    private String email;
}
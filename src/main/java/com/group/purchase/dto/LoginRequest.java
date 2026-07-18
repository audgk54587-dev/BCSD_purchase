//로그인을 시도할 때 사용자가 입력한 이메일과 비밀번호를 담아 전달하는 객체
package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}

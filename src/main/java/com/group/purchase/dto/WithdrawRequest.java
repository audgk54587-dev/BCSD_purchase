//회원 탈퇴를 요청할 때 본인 확인을 위한 정보(주로 비밀번호)를 담아 서버에 전달하는 객체
package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class WithdrawRequest {
    private String email;
    private String password;
}
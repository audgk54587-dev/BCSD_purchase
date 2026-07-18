//비밀번호 찾기(또는 재설정)를 위해 사용자의 식별 정보(이메일, 이름 등)를 담는 객체
package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class FindPwRequest {
    private String email;
    private String name;
}
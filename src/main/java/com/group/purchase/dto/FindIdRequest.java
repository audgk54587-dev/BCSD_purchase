//아이디(이메일) 찾기를 위해 사용자의 이름과 전화번호를 담아 서버로 보내는 객체
package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class FindIdRequest {
    private String name;
    private String phoneNumber;
}
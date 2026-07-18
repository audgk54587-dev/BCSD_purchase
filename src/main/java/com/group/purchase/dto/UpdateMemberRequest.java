//회원 정보 수정 시 사용자가 변경하려고 입력한 새로운 정보들(이름, 연락처 등)을 담는 객체
package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class UpdateMemberRequest {
    private String email; // 누구의 정보를 바꿀지 식별하기 위함
    private String name;
    private String phoneNumber;
    private String accountNumber;
}
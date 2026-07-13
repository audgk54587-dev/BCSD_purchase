package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class UpdateMemberRequest {
    private String email; // 누구의 정보를 바꿀지 식별하기 위함
    private String name;
    private String phoneNumber;
    private String accountNumber;
}
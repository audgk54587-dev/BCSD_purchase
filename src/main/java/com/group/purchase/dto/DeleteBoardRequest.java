//해당 글을 지울 권한이 있는지 검증을 하기 위한 정보(예: 이메일)를 담아 전달
package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class DeleteBoardRequest {
    private String email;
}
//새로운 모집글을 작성할 때 사용자가 입력한 제목, 내용, 목표 인원 등의 폼 데이터를 서버로 안전하게 전달
package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class CreateBoardRequest {
    private String email;
    private String title;
    private String content;
    private int targetParticipants;
}
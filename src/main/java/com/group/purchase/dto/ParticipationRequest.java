//어떤 사용자가 참여하는지 식별하기 위한 데이터를 서버로 전달하는 파일
package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class ParticipationRequest {
    private String email;
}
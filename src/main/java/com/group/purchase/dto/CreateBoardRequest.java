package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class CreateBoardRequest {
    private String email;
    private String title;
    private String content;
}
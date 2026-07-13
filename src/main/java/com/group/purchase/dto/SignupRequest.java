package com.group.purchase.dto;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String accountNumber;
}

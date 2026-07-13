package com.group.purchase.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    private final ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();

    public void sendVerificationCode(String email) {
        String code = String.valueOf((int) (Math.random() * 899999) + 100000);

        verificationCodes.put(email, code);

        System.out.println("========================================");
        System.out.println("가상의 이메일 발송 성공!");
        System.out.println("수신자: " + email);
        System.out.println("인증코드: [" + code + "]");
        System.out.println("========================================");
    }

    public boolean verifyCode(String email, String inputCode) {
        String storedCode = verificationCodes.get(email);

        if (storedCode != null && storedCode.equals(inputCode)) {
            verificationCodes.remove(email);
            return true;
        }
        return false;
    }
}
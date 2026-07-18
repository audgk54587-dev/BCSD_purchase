package com.group.purchase.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    private final ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();
    //발급된 인증 코드를 임시로 저장
    //ConcurrentHashMap을 쓴 이유: 여러 사용자가 동시에 회원가입을 시도하여 이 클래스에 접근할 때 데이터가 꼬이는 것(동시성 문제)을 방지

    public void sendVerificationCode(String email) {
        String code = String.valueOf((int) (Math.random() * 899999) + 100000);
        //6자리 랜덤 인증 번호 생성 로직
        //String.valueOf(): 문자열로 변환

        verificationCodes.put(email, code);
        //6자리 인증 코드를 사용자의 email을 키(Key)로 삼아 앞서 만든 메모리 저장소(ConcurrentHashMap)에 저장

        System.out.println("========================================");
        System.out.println("가상의 이메일 발송 성공!");
        System.out.println("수신자: " + email);
        System.out.println("인증코드: [" + code + "]");
        System.out.println("========================================");
    }

    public boolean verifyCode(String email, String inputCode) {
        String storedCode = verificationCodes.get(email);
        //메모리 저장소에서 해당 email로 저장되어 있던 진짜 인증 코드를 꺼냄
        if (storedCode != null && storedCode.equals(inputCode)) {
            //메일에 해당하는 코드가 저장소에 실제로 존재하고 용자가 방금 입력한 코드와 완벽히 일치한다면
            verificationCodes.remove(email);
            //메모리 저장소에서 해당 이메일의 인증 코드 기록을 삭제 -> 재사용을 막기 위함
            return true;
        }
        return false;
    }
}
package com.group.purchase.controller;

import com.group.purchase.dto.*;
import com.group.purchase.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group.purchase.service.EmailService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/email-code")
    public ResponseEntity<String> sendEmailCode(@RequestBody EmailRequest request) {
        emailService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok("인증번호가 발송되었습니다. 이메일을 확인해 주세요.");
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestBody VerifyRequest request) {
        boolean isVerified = emailService.verifyCode(request.getEmail(), request.getCode());

        if (isVerified) {
            return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("인증번호가 일치하지 않거나 만료되었습니다.");
        }
    }

    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody FindIdRequest request) {
        String email = authService.findId(request);
        return ResponseEntity.ok("회원님의 아이디(이메일)는 [" + email + "] 입니다.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody FindPwRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("임시 비밀번호가 가상 이메일(콘솔)로 발송되었습니다.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateMember(@RequestBody UpdateMemberRequest request) {
        authService.updateMember(request);
        return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawRequest request) {
        authService.withdraw(request);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}
package com.group.purchase.service;

import com.group.purchase.domain.Member;
import com.group.purchase.dto.*;
import com.group.purchase.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.group.purchase.config.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider; // 추가

    @Transactional
    public Long signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Member member = Member.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .accountNumber(request.getAccountNumber())
                .build();

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.createToken(member.getEmail());
    }

    @Transactional(readOnly = true)
    public String findId(FindIdRequest request) {
        Member member = memberRepository.findByNameAndPhoneNumber(request.getName(), request.getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("입력하신 정보와 일치하는 회원이 없습니다."));

        return member.getEmail();
    }

    @Transactional
    public void resetPassword(FindPwRequest request) {
        Member member = memberRepository.findByEmailAndName(request.getEmail(), request.getName())
                .orElseThrow(() -> new IllegalArgumentException("입력하신 정보와 일치하는 회원이 없습니다."));

        String tempPassword = java.util.UUID.randomUUID().toString().substring(0, 8);

        member.changePassword(passwordEncoder.encode(tempPassword));

        System.out.println("========================================");
        System.out.println("가상의 이메일 발송 성공!");
        System.out.println("수신자: " + member.getEmail());
        System.out.println("임시 비밀번호: [" + tempPassword + "]");
        System.out.println("로그인 후 반드시 비밀번호를 변경해 주세요.");
        System.out.println("========================================");
    }

    @Transactional
    public void updateMember(UpdateMemberRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        member.updateInfo(request.getName(), request.getPhoneNumber(), request.getAccountNumber());
    }

    @Transactional
    public void withdraw(WithdrawRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않아 탈퇴할 수 없습니다.");
        }

        memberRepository.delete(member);
    }
}

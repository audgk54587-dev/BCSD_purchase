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
//Lombok 기능, final이 붙은 필드들의 생성자를 자동으로 만듦 -> 스프링이 자동으로 필요한 의존성 주입
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    //데이터베이스 작업들을 하나의 트랜잭션으로 묶음
    public Long signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            //request.getEmail(): 사용자가 입력한 이메일
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
            //true라면, 회원가입 절차를 즉시 중단하고 예외처리
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        //.encode(): 해시값으로 변환

        Member member = Member.builder()
                //엔티티(Entity) 객체'를 생성할 준비
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .accountNumber(request.getAccountNumber())
                .build();   //build()를 호출 -> 온전한 Member 객체 1개로 조립

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));
                //조회한 Optional 객체가 비어있다면 즉시 중지 및 예외 처리

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            //passwordEncoder.matches(평문, 암호문): 두 값이 일치하는지 비교, 일치하지 않으면 true
            //request.getPassword(): 사용자가 방금 화면에서 입력한 암호화되지 않은 '평문' 비밀번호
            //member.getPassword(): DB에서 가져온, 이미 해시로 '암호화된' 비밀번호
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.createToken(member.getEmail());
        //jwtProvider -> 해당 회원의 이메일을 기반으로 새로운 JWT 토큰을 생성 및 반환
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
        //임시 비밀번호를 생성
        //UUID.randomUUID(): 전 세계에서 유일한 고유 식별자 무작위 생성
        //.toString().substring(0, 8): 생성된 문자열에서 맨 앞에서부터 8글자만 잘라내어 tempPassword에 임시 비밀번호로 저장

        member.changePassword(passwordEncoder.encode(tempPassword));
        //임시 평문 비밀번호를 passwordEncoder를 통해 해시값으로 안전하게 암호화
        //내부 비밀번호 값을 암호화된 값으로 바꿈

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
        //새로운 이름, 새로운 전화번호, 새로운 계좌번호를 받아서 수정
    }

    @Transactional
    public void withdraw(WithdrawRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            //암호화되지 않은 원래 비밀번호(평문)와 암호화된 비밀번호가 같지 않다면
            throw new IllegalArgumentException("비밀번호가 일치하지 않아 탈퇴할 수 없습니다.");
        }

        memberRepository.delete(member);
    }
}

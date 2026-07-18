package com.group.purchase.repository;

import com.group.purchase.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
    //전달받은 email로 데이터베이스에서 회원을 검색하여 반환
    Optional<Member> findByNameAndPhoneNumber(String name, String phoneNumber);
    //전달받은 이름과 전화번호가 모두 일치하는 회원을 검색
    Optional<Member> findByEmailAndName(String email, String name);
    //전달받은 이메일과 이름이 모두 일치하는 회원을 검색
}
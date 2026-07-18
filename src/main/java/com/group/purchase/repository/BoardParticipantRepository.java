package com.group.purchase.repository;

import com.group.purchase.domain.Board;
import com.group.purchase.domain.BoardParticipant;
import com.group.purchase.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BoardParticipantRepository extends JpaRepository<BoardParticipant, Long> {
    // 이미 참여한 유저인지 확인하는 메서드
    boolean existsByBoardAndMember(Board board, Member member);
    // 취소를 위해 참여 기록을 찾는 메서드
    Optional<BoardParticipant> findByBoardAndMember(Board board, Member member);
}
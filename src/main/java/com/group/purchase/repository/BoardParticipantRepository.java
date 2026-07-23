package com.group.purchase.repository;

import com.group.purchase.domain.Board;
import com.group.purchase.domain.BoardParticipant;
import com.group.purchase.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardParticipantRepository extends JpaRepository<BoardParticipant, Long> {

    // 이미 참여한 유저인지 확인하는 메서드
    boolean existsByBoardAndMember(Board board, Member member);

    // 취소를 위해 참여 기록을 찾는 메서드
    Optional<BoardParticipant> findByBoardAndMember(Board board, Member member);

    //게시글 삭제 시 엮여있는 참여 기록들을 일괄 삭제하기 위한 메서드
    void deleteAllByBoard(Board board);

    // 특정 모집글에 참여한 모든 사람의 기록을 리스트로 가져옴
    List<BoardParticipant> findAllByBoard(Board board);
}
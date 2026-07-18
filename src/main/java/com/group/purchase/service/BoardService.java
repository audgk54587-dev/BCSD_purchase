package com.group.purchase.service;

import com.group.purchase.domain.Board;
import com.group.purchase.domain.Member;
import com.group.purchase.dto.BoardResponse;
import com.group.purchase.dto.CreateBoardRequest;
import com.group.purchase.dto.DeleteBoardRequest;
import com.group.purchase.repository.BoardRepository;
import com.group.purchase.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    //트랜잭션을 적용 -> 중간에 에러가 발생하면 저장하려던 내용이 모두 취소
    public void createBoard(CreateBoardRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자입니다."));

        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(member)
                .build();

        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    //읽기 전용 트랜잭션
    public List<BoardResponse> getAllBoards(String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        //삼항 연산자(조건 ? 참일 때 값 : 거짓일 때 값)
        //sortOrder.equalsIgnoreCase("asc"): 클라이언트가 보낸 문자열이 대소문자 구분 없이 "asc"(과거순)와 같다면
        //Sort.Direction.ASC: 스프링 데이터 JPA의 오름차순 정렬
        //: Sort.Direction.DESC: 내림차순(최신순) 정렬

        List<Board> boards = boardRepository.findAll(Sort.by(direction, "createdAt"));
        //Sort.by(direction, "createdAt"): 앞서 결정한 정렬 방향 적용 -> 엔티티의 createdAt(작성 시간) 필드를 기준으로 정렬

        return boards.stream()
                //스트림(Stream) API 시작
                .map(BoardResponse::new)
                //Board 엔티티 객체를 BoardResponse DTO 객체로 변환(매핑)
                .collect(Collectors.toList());
                //DTO로 변환되어 있던 객체들을 모음(collect)
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return new BoardResponse(board);
    }


    @Transactional
    public void deleteBoard(Long id, DeleteBoardRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!board.getAuthor().getEmail().equals(request.getEmail())) {
            throw new IllegalArgumentException("해당 게시글을 삭제할 권한이 없습니다.");
        }

        boardRepository.delete(board);
    }
}
package com.group.purchase.controller;

import com.group.purchase.dto.BoardResponse;
import com.group.purchase.dto.CreateBoardRequest;
import com.group.purchase.dto.DeleteBoardRequest;
import com.group.purchase.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group.purchase.dto.ParticipationRequest;

import java.util.List;

@RestController
//이 클래스가 JSON을 응답으로 하는 RESTful 웹 서비스의 컨트롤러임을 스프링에 알려줌
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //모집글 생성 API
    @PostMapping
    public ResponseEntity<String> createBoard(@RequestBody CreateBoardRequest request) {
        boardService.createBoard(request);
        return ResponseEntity.ok("모집글이 성공적으로 등록되었습니다.");
    }

    //모집글 전체 목록 조회 API
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards(
            @RequestParam(value = "sort", defaultValue = "desc") String sortOrder) {
            //@RequestParam: 클라이언트가 URL 끝에 ?sort=asc 처럼 붙여서 보낸 쿼리 파라미터 값을 받음
            //defaultValue = "desc": 기본 세팅 "desc"
        List<BoardResponse> boards = boardService.getAllBoards(sortOrder);
        return ResponseEntity.ok(boards);
    }

    //모집글 상세 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long id) {
        //@PathVariable: URL 경로에 뚫어놓은 {id}에 들어온 숫자 값을 가져옴
        BoardResponse board = boardService.getBoard(id);
        return ResponseEntity.ok(board);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id, @RequestBody DeleteBoardRequest request) {
        //@RequestBody: 클라이언트가 요청 본문(Body)에 담아서 보낸 JSON 데이터
        boardService.deleteBoard(id, request);
        return ResponseEntity.ok("모집글이 성공적으로 삭제되었습니다.");
    }

    //신청 버튼 클릭
    @PostMapping("/{id}/participate")
    public ResponseEntity<String> participate(@PathVariable Long id, @RequestBody ParticipationRequest request) {
        boardService.participate(id, request);
        //컨트롤러가 직접 데이터를 수정하지 않고, boardService로 요청을 위임
        return ResponseEntity.ok("성공적으로 참여하였습니다.");
    }

    //취소 버튼 클릭
    @DeleteMapping("/{id}/participate")
    public ResponseEntity<String> cancelParticipation(@PathVariable Long id, @RequestBody ParticipationRequest request) {
        boardService.cancelParticipation(id, request);
        return ResponseEntity.ok("참여가 취소되었습니다.");
    }
}
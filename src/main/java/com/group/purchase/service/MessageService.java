package com.group.purchase.service;

import com.group.purchase.domain.Board;
import com.group.purchase.domain.Member;
import com.group.purchase.dto.DirectMessageRequest;
import com.group.purchase.repository.BoardParticipantRepository;
import com.group.purchase.repository.BoardRepository;
import com.group.purchase.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardParticipantRepository participantRepository;

    @Transactional(readOnly = true)
    public void sendMessage(DirectMessageRequest request) {
        //게시글, 발신자, 수신자 엔티티 조회
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Member sender = memberRepository.findByEmail(request.getSenderEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 발신자입니다."));
        Member receiver = memberRepository.findByEmail(request.getReceiverEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수신자입니다."));

        // 권한 검증: 문자를 보내려는 사람(sender)이 해당 게시글의 진짜 작성자가 맞는지 확인
        if (!board.getAuthor().getEmail().equals(sender.getEmail())) {
            //작성자의 이메일이 보내는 사람의 이메일과 같지 않다면 -> 예외 처리
            throw new IllegalArgumentException("게시글 작성자만 참가자에게 문자를 보낼 수 있습니다.");
        }

        // 수신자 검증: 문자를 받을 사람(receiver)이 실제로 공동구매에 참여한 사람이 맞는지 확인
        if (!participantRepository.existsByBoardAndMember(board, receiver)) {
            throw new IllegalArgumentException("해당 게시글에 참여한 사용자가 아닙니다.");
        }

        // 문자 내용(템플릿) 생성
        int quantityPerPerson = board.getTotalQuantity() / board.getTargetParticipants();
        // 1인당 구매 개수 = 총 구매 개수 / 목표 인원
        int pricePerPerson = quantityPerPerson * board.getUnitPrice();
        // 1인당 결제 금액 = 1인당 구매 개수 * 단가
        String autoMessage = String.format(
                "안녕하세요! [%s] 공동구매 안내입니다.\n" +
                        " - 현인원/모집 인원 : %d / %d 명\n" +
                        " - 총 구매 개수   : %d 개 (1인당 %d개)\n" +
                        " - 단가           : %d 원\n" +
                        " - 총 결제 금액   : %d 원\n" +
                        " - 입금 계좌      : %s\n" +
                        " - 입금 기한      : %s\n\n" +
                        "확인 후 기한 내에 입금 부탁드립니다!",
                board.getTitle(),
                board.getCurrentParticipants(), board.getTargetParticipants(),
                board.getTotalQuantity(), quantityPerPerson,
                board.getUnitPrice(),
                pricePerPerson,
                sender.getAccountNumber(),
                board.getDeadline()
        );

        //출력
        System.out.println("========================================");
        System.out.println("📱 [자동 안내 문자 발송 성공]");
        System.out.println("수신(참가자) : " + receiver.getName());
        System.out.println("[문자 내용]\n" + autoMessage);
        System.out.println("========================================");
    }
}
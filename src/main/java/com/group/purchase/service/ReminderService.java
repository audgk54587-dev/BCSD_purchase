package com.group.purchase.service;

import com.group.purchase.domain.Board;
import com.group.purchase.domain.BoardParticipant;
import com.group.purchase.repository.BoardParticipantRepository;
import com.group.purchase.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final BoardRepository boardRepository;
    private final BoardParticipantRepository participantRepository;

    // 1분(60000밀리초)마다 자동으로 아래 메서드를 실행합니다.
    @Scheduled(fixedRate = 60000)
    @Transactional(readOnly = true)
    public void checkAndSendReminders() {
        List<Board> boards = boardRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        // 데이터베이스에 저장될 날짜 형식 (포스트맨 입력 시 이 형식을 지켜야 합니다)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Board board : boards) {
            try {
                // String으로 저장된 마감기한을 컴퓨터가 계산할 수 있는 시간(LocalDateTime)으로 변환
                LocalDateTime deadline = LocalDateTime.parse(board.getDeadline(), formatter);

                // 마감일 정확히 24시간(1일) 전 시간 계산
                LocalDateTime oneDayBefore = deadline.minusDays(1);

                // 현재 시간이 '마감 1일 전'과 '마감 1일 전 + 1분' 사이라면 알림 발송 (중복 발송 방지용 1분 텀)
                if (now.isAfter(oneDayBefore) && now.isBefore(oneDayBefore.plusMinutes(1))) {

                    List<BoardParticipant> participants = participantRepository.findAllByBoard(board);

                    for (BoardParticipant participant : participants) {
                        System.out.println("========================================");
                        System.out.println("⏰ [리마인드 알림 발송]");
                        System.out.println("수신자 : " + participant.getMember().getName());
                        System.out.println("내용   : [" + board.getTitle() + "] 공동구매 입금 기한이 1일 남았습니다!");
                        System.out.println("         잊지 말고 내일 " + deadline.getHour() + "시 " + deadline.getMinute() + "분까지 입금해 주세요.");
                        System.out.println("========================================");
                    }
                }
            } catch (DateTimeParseException e) {
                // 기존에 한글("오후 6시" 등)로 저장된 잘못된 형식의 글들은 변환을 실패하므로 안전하게 무시하고 넘깁니다.
                continue;
            }
        }
    }
}
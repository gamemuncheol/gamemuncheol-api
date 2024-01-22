package com.gamemoonchul.application.command;

import com.gamemoonchul.common.exception.SimpleLogicException;
import com.gamemoonchul.domain.Ticket;
import com.gamemoonchul.infrastructure.persistence.LectureJpaRepository;
import com.gamemoonchul.infrastructure.persistence.TicketJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TicketCommandHandler {

    final TicketJpaRepository ticketJpaRepository;
    final LectureJpaRepository lectureJpaRepository;

    @Transactional
    public Ticket handler(TicketRegistrationCommand command) {

        var ticket = ticketJpaRepository.findByLectureIdAndEmployeeId(command.getLectureId(),
                command.getEmployeeId());
        if (ticket != null && ticket.isPresent()) {
            throw new SimpleLogicException("이미 수강신청 하였습니다.");
        }

        var lecture = lectureJpaRepository.findByIdForUpdate(command.getLectureId())
                .orElseThrow(() -> new SimpleLogicException("Not Found Entity"));
        lecture.increaseAttendeeCount();
        return ticketJpaRepository.save(command.create(lecture));
    }
}

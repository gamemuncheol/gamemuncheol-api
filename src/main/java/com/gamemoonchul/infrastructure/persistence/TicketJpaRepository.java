package com.gamemoonchul.infrastructure.persistence;

import com.gamemoonchul.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketJpaRepository extends JpaRepository<Ticket, Long> {
    Ticket save(Ticket Ticket);
    Optional<Ticket> findByLectureIdAndEmployeeId(Long lectureId, String employeeId);
}

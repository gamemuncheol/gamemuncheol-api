package com.gamemoonchul.application.command;

import com.gamemoonchul.domain.Lecture;
import com.gamemoonchul.domain.Ticket;
import lombok.Getter;

@Getter
public class TicketRegistrationCommand {

    private Long lectureId;
    private String employeeId;

    public TicketRegistrationCommand(Long lectureId, String employeeId) {
        this.lectureId = lectureId;
        this.employeeId = employeeId;
    }

    public static TicketRegistrationCommand of(Long lectureId, String employeeId) {
        return new TicketRegistrationCommand(lectureId, employeeId);
    }

    public Ticket create(Lecture lecture) {
        return Ticket.builder()
                .employeeId(employeeId)
                .lecture(lecture)
                .build();
    }
}

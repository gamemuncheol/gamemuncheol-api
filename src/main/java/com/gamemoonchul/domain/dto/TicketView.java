package com.gamemoonchul.domain.dto;

import com.gamemoonchul.domain.Ticket;

public record TicketView(
        Long id,
        Long lectureId,
        String employeeId
) {
    public static TicketView from(Ticket ticket) {
        return new TicketView(
                ticket.getId(),
                ticket.getLecture().getId(),
                ticket.getEmployeeId());
    }
}

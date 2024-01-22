package com.gamemoonchul.infrastructure.web.dto;

import com.gamemoonchul.application.command.TicketRegistrationCommand;
import com.gamemoonchul.application.command.TicketRegistrationCommand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TicketCreateRequest(
        @NotEmpty
        @Size(min = 5, max = 5)
        String employeeId
) {
        public TicketRegistrationCommand toCommand(Long lectureId) {
                return TicketRegistrationCommand.of(
                        lectureId,
                        employeeId
                );
        }
}
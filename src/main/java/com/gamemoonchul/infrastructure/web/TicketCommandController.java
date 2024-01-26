package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.LectureQueryService;
import com.gamemoonchul.application.command.TicketCommandHandler;
import com.gamemoonchul.domain.dto.TicketView;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.TicketCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
public class TicketCommandController {

    private final LectureQueryService lectureQueryService;
    private final TicketCommandHandler ticketCommandHandler;

    @PostMapping("/api/v1/lectures/{lecture_id}/tickets")
    public TicketView create(@Valid @RequestBody TicketCreateRequest request,
                             @PathVariable(name = "lecture_id") Long lectureId) {
        var savedTicket = ticketCommandHandler.handler(request.toCommand(lectureId));
        return TicketView.from(savedTicket);
    }

}

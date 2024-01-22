package com.gamemoonchul.infrastructure.web.dto;

import com.gamemoonchul.application.command.LectureRegistrationCommand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LectureCreateRequest(
        @NotEmpty
        String speaker,

        @NotEmpty
        String venue,

        @NotEmpty
        String content,

        @NotNull
        int capacityOfAttendee,

        @NotNull
        LocalDateTime startAt
) {
    public LectureRegistrationCommand toCommand() {
        return LectureRegistrationCommand.of(
                speaker,
                venue,
                content,
                capacityOfAttendee,
                startAt
        );
    }

}
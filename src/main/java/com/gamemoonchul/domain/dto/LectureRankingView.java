package com.gamemoonchul.domain.dto;

import java.time.LocalDateTime;

public record LectureRankingView(

        Long id,
        String speaker,
        String venue,
        String content,
        int capacityOfAttendee,
        int attendeeCount,
        LocalDateTime startAt,

        int attendeeCountFor3Days
) {
}

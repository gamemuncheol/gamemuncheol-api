package com.gamemoonchul.domain.dto;

import com.gamemoonchul.domain.Lecture;

import java.time.LocalDateTime;

public record LectureDto(

        Long id,

        String speaker,

        String venue,

        String content,

        int capacityOfAttendee,

        int attendeeCount,

        LocalDateTime startAt
) {
    public static LectureDto from(Lecture lecture) {
        return new LectureDto(
                lecture.getId(),
                lecture.getSpeaker(),
                lecture.getVenue(),
                lecture.getContent(),
                lecture.getCapacityOfAttendee(),
                lecture.getAttendeeCount(),
                lecture.getStartAt());
    }
}

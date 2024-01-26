package com.gamemoonchul.domain.dto;

import com.gamemoonchul.domain.Lecture;

import java.time.LocalDateTime;

public record LectureView(

        Long id,
        String speaker,
        String venue,
        String content,
        int capacityOfAttendee,
        int attendeeCount,
        LocalDateTime startAt
) {
    public static LectureView from(Lecture lecture) {
        return new LectureView(
                lecture.getId(),
                lecture.getSpeaker(),
                lecture.getVenue(),
                lecture.getContent(),
                lecture.getCapacityOfAttendee(),
                lecture.getAttendeeCount(),
                lecture.getStartAt());
    }
}

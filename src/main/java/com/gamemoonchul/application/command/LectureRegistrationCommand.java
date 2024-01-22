package com.gamemoonchul.application.command;

import com.gamemoonchul.domain.Lecture;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LectureRegistrationCommand {

    private String speaker;  // 강연자
    private String venue;  // 강연장
    private String content; // 강연내용
    private int capacityOfAttendee;  // 신청인원
    private LocalDateTime startAt; // 강연시간

    public LectureRegistrationCommand(String speaker,
                                      String venue,
                                      String content,
                                      int capacityOfAttendee,
                                      LocalDateTime startAt) {
        this.speaker = speaker;
        this.venue = venue;
        this.content = content;
        this.capacityOfAttendee = capacityOfAttendee;
        this.startAt = startAt;
    }

    public static LectureRegistrationCommand of(String speaker,
                                                String venue,
                                                String content,
                                                int capacityOfAttendee,
                                                LocalDateTime startAt) {
        return new LectureRegistrationCommand(speaker, venue, content, capacityOfAttendee, startAt);
    }

    public Lecture create() {
        return Lecture.builder()
                .speaker(speaker)
                .capacityOfAttendee(capacityOfAttendee)
                .venue(venue)
                .content(content)
                .startAt(startAt)
                .build();
    }
}

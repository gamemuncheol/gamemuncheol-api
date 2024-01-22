package com.gamemoonchul.domain;

import com.gamemoonchul.common.BaseTimeEntity;
import com.gamemoonchul.common.exception.SimpleLogicException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 강연 도메인
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
@Entity(name = "lectures")
public class Lecture extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "speaker", nullable = false)
    private String speaker;  // 강연자

    @Column(name = "venue", nullable = false)
    private String venue;  // 강연장

    @Lob
    @Column(name = "content", nullable = false)
    private String content; // 강연내용


    @Column(name = "capacity_of_attenedee", columnDefinition = "integer default 0 COMMENT '신청인원'")
    private int capacityOfAttendee;  // 신청인원

    @Column(name = "attendeeCount", columnDefinition = "integer default 0 COMMENT '참석인원'")
    private int attendeeCount;  // 현재 참석인원


    @Column(name = "startAt", nullable = false, columnDefinition = "DATETIME(6) COMMENT '강연시각'")
    private LocalDateTime startAt; // 강연시간

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, orphanRemoval = true)
    List<Ticket> tickets = new ArrayList<>();

    @Builder
    public Lecture(String speaker,
                   String venue,
                   String content,
                   int attendeeCount,
                   int capacityOfAttendee,
                   LocalDateTime startAt) {
        this.speaker = speaker;
        this.venue = venue;
        this.content = content;
        this.attendeeCount = attendeeCount;
        this.capacityOfAttendee = capacityOfAttendee;
        this.startAt = startAt;
    }

    public Lecture increaseAttendeeCount() {
        attendeeCount += 1;
        if (attendeeCount > capacityOfAttendee) {
            throw new SimpleLogicException("수강인원 초과");
        }
        return this;
    }

    public Lecture decreaseAttendeeCount() {
        this.attendeeCount -= 1;
        if (attendeeCount < 0) {
            throw new SimpleLogicException("수강인원 취소 오류");
        }
        return this;
    }
}
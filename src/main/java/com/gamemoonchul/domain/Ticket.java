package com.gamemoonchul.domain;

import com.gamemoonchul.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 티켓 도메인
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_lecture_employee", columnNames = {"lecture_id", "employee_id"})
})
@Entity(name = "tickets")
public class Ticket extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(name = "employee_id")
    private String employeeId;

    @Builder
    public Ticket(Lecture lecture, String employeeId) {
        this.lecture = lecture;
        this.employeeId = employeeId;
    }
}

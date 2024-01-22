package com.gamemoonchul.application.command;

import com.gamemoonchul.domain.Lecture;
import com.gamemoonchul.infrastructure.persistence.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LectureCommandHandler {

    final LectureJpaRepository lectureJpaRepository;

    public Lecture handler(LectureRegistrationCommand command) {
        return lectureJpaRepository.save(command.create());
    }
}

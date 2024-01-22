package com.gamemoonchul.infrastructure.persistence;

import com.gamemoonchul.domain.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
    Lecture save(Lecture entity);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from lectures p where p.id = :lectureId")
    Optional<Lecture> findByIdForUpdate(@Param("lectureId") long lectureId);
}
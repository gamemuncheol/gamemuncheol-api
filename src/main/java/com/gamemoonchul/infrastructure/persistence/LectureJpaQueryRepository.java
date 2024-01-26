package com.gamemoonchul.infrastructure.persistence;

import com.gamemoonchul.domain.dto.LectureRankingView;
import com.gamemoonchul.domain.dto.LectureSearchCondition;
import com.gamemoonchul.domain.dto.LectureView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LectureJpaQueryRepository {
    Page<LectureView> search(LectureSearchCondition condition, Pageable pageable);
    List<String> getAttendees(Long lectureId);
    List<LectureRankingView> getPopularLectures();
}

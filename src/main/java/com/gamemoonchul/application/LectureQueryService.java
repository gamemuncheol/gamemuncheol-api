package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.SimpleLogicException;
import com.gamemoonchul.domain.dto.LectureDto;
import com.gamemoonchul.domain.dto.LectureRankingView;
import com.gamemoonchul.domain.dto.LectureSearchCondition;
import com.gamemoonchul.domain.dto.LectureView;
import com.gamemoonchul.infrastructure.persistence.LectureJpaQueryRepository;
import com.gamemoonchul.infrastructure.persistence.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class LectureQueryService {
    private final LectureJpaQueryRepository lectureJpaQueryRepository;
    private final LectureJpaRepository lectureJpaRepository;

    @Transactional(readOnly = true)
    public Page<LectureView> search(LectureSearchCondition condition, Pageable pageRequest) {
        return lectureJpaQueryRepository.search(condition, pageRequest);
    }

    @Transactional(readOnly = true)
    public LectureDto getById(Long lectureId) {
        var lecture = lectureJpaRepository.findById(lectureId)
                .orElseThrow(() -> new SimpleLogicException("Not found entity"));
        return LectureDto.from(lecture);
    }

    @Transactional(readOnly = true)
    public List<String> getAttendees(Long lectureId) {
        return lectureJpaQueryRepository.getAttendees(lectureId);
    }

    @Transactional(readOnly = true)
    public List<LectureRankingView> getRank() {
        return lectureJpaQueryRepository.getPopularLectures();
    }
}

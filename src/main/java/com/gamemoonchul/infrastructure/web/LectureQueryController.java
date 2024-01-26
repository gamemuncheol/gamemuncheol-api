package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.LectureQueryService;
import com.gamemoonchul.domain.dto.LectureRankingView;
import com.gamemoonchul.domain.dto.LectureSearchCondition;
import com.gamemoonchul.domain.dto.LectureView;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
public class LectureQueryController {
    private final LectureQueryService service;

    @GetMapping("/api/v1/lectures")
    public Page<LectureView> search(LectureSearchCondition condition, Pageable pageable) {
        var ser = service.search(condition, pageable);
        return ser;
    }

    @GetMapping("/api/v1/lectures/{lecture_id}/attendees")
    public List<String> getAttendees(@PathVariable(name = "lecture_id") Long lectureId) {
        return service.getAttendees(lectureId);
    }

    @GetMapping("/api/v1/lectures/rank")
    public List<LectureRankingView> getRank() {
        return service.getRank();
    }
}

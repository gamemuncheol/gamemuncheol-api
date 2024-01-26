package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.command.LectureCommandHandler;
import com.gamemoonchul.domain.dto.LectureView;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.LectureCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
public class LectureCommandController {
    private final LectureCommandHandler commandHandler;

    @PostMapping("/api/v1/lectures")
    public LectureView create(@RequestBody @Valid LectureCreateRequest request) {
        var lecture = commandHandler.handler(request.toCommand());
        return LectureView.from(lecture);
    }
}

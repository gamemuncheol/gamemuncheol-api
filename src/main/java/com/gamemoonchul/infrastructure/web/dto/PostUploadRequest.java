package com.gamemoonchul.infrastructure.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record PostUploadRequest(
        @NotNull
        String videoUrl,
        @NotNull
        String thumbnailUrl,
        @Max(50)
        String title,
        String content,
        @Size(min = 2)
        List<Long> matchUserIds,
        List<String> tags
) {
}

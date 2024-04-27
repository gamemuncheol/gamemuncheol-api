package com.gamemoonchul.infrastructure.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PostUploadRequest(
        @NotNull
        String videoUrl,
        @NotNull
        String thumbnailUrl,
        @Max(50)
        String title,
        String content
) {
}

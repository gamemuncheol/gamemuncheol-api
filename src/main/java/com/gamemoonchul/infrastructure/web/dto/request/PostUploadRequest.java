package com.gamemoonchul.infrastructure.web.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

@Builder
public record PostUploadRequest(
        @NotNull
        @NotEmpty
        String videoUrl,
        @NotNull
        @NotEmpty
        String thumbnailUrl,
        @NotNull
        @Size(min = 1, max = 50) // Not Empty를 대신함
        String title,
        String content,
        @Size(min = 2, max = 2)
        List<Long> matchUserIds,
        List<String> tags
) {
}

package com.gamemoonchul.infrastructure.web.dto;

import lombok.Builder;

@Builder
public record CommentFixRequest(
        Long commentId,
        String contents
) {
}

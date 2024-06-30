package com.gamemoonchul.infrastructure.web.dto.request;

import lombok.Builder;

@Builder
public record CommentFixRequest(
        Long commentId,
        String contents
) {
}

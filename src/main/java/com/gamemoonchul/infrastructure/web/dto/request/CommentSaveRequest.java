package com.gamemoonchul.infrastructure.web.dto.request;

import lombok.Builder;

@Builder
public record CommentSaveRequest(
    Long parentId,
    String content,
    Long postId
) {
}

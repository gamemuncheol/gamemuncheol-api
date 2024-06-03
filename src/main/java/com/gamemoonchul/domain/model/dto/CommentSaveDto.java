package com.gamemoonchul.domain.model.dto;

import com.gamemoonchul.infrastructure.web.dto.CommentRequest;
import lombok.Builder;

@Builder
public record CommentSaveDto (
        Long parentId,
        String content,
        Long postId
) {
}

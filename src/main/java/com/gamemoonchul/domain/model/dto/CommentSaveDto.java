package com.gamemoonchul.domain.model.dto;

import lombok.Builder;

@Builder
public record CommentSaveDto(
        Long parentId,
        String content,
        Long postId
) {
}

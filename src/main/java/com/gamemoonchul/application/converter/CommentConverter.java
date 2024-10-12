package com.gamemoonchul.application.converter;

import com.gamemoonchul.common.util.StringUtils;
import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.infrastructure.web.dto.response.CommentResponse;

public class CommentConverter {
    public static CommentResponse toResponse(Comment entity) {
        return CommentResponse.builder()
            .author(MemberConverter.toResponseDto(entity.getMember()))
            .content(entity.getContent())
            .timesAgo(StringUtils.getTimeAgo(entity.getCreatedAt()))
            .build();
    }

}

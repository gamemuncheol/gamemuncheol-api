package com.gamemoonchul.infrastructure.web.dto.response;

import com.gamemoonchul.application.converter.MemberConverter;
import com.gamemoonchul.common.util.StringUtils;
import com.gamemoonchul.domain.entity.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponse {
    private MemberResponse author;
    private String content;
    private String timesAgo;

    public static CommentResponse entityToResponse(Comment entity) {
        return CommentResponse.builder()
            .author(MemberConverter.toResponseDto(entity.getMember()))
            .content(entity.getContent())
            .timesAgo(StringUtils.getTimeAgo(entity.getCreatedAt()))
            .build();
    }
}

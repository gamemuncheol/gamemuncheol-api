package com.gamemoonchul.infrastructure.web.dto.response;

import com.gamemoonchul.application.member.MemberConverter;
import com.gamemoonchul.common.util.StringUtils;
import com.gamemoonchul.domain.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
    private MemberResponseDto author;
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

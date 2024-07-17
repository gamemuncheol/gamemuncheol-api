package com.gamemoonchul.infrastructure.web.dto.response;

import com.gamemoonchul.application.converter.MemberConverter;
import com.gamemoonchul.common.util.StringUtils;
import com.gamemoonchul.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostMainPageResponse {
    private Long id;
    private MemberResponseDto member;
    private String videoUrl;
    private String thumbnailUrl;
    private String title;
    private String content;
    private String timesAgo;
    private Long viewCount;
    private List<Double> voteRatio;

    public static PostMainPageResponse entityToResponse(Post entity) {
        List<Double> voteRatio = List.of(100 - entity.getVoteRatio(), entity.getVoteRatio());
        return PostMainPageResponse.builder()
                .id(entity.getId())
                .member(MemberConverter.toResponseDto(entity.getMember()))
                .videoUrl(entity.getVideoUrl())
                .thumbnailUrl(entity.getThumbnailUrl())
                .title(entity.getTitle())
                .content(entity.getContent())
                .viewCount(entity.getViewCount())
                .timesAgo(StringUtils.getTimeAgo(entity.getCreatedAt()))
                .voteRatio(voteRatio)
                .build();
    }

}
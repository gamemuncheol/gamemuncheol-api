package com.gamemoonchul.infrastructure.web.dto.response;

import com.gamemoonchul.common.util.StringUtils;
import com.gamemoonchul.domain.entity.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
            .member(new MemberResponseDto(entity.getMember()))
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

package com.gamemoonchul.infrastructure.web.dto;

import java.time.Duration;

import com.gamemoonchul.application.converter.MemberConverter;
import com.gamemoonchul.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
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
                .timesAgo(getTimeAgo(entity.getCreatedAt()))

                .voteRatio(voteRatio)
                .build();
    }

    public static String getTimeAgo(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        if (duration.isNegative() || duration.isZero()) {
            return "방금 전";
        }

        long days = duration.toDays();
        if (days > 0) {
            return days + "일 전";
        }

        long hours = duration.toHours();
        if (hours > 0) {
            return hours + "시간 전";
        }

        long minutes = duration.toMinutes();
        if (minutes > 0) {
            return minutes + "분 전";
        }

        long seconds = duration.getSeconds();
        if (seconds > 0) {
            return seconds + "초 전";
        }

        return "방금 전";
    }
}

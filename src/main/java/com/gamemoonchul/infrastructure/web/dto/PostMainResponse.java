package com.gamemoonchul.infrastructure.web.dto;

import com.gamemoonchul.application.converter.MemberConverter;
import com.gamemoonchul.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Builder
public class PostMainResponse {
    private Long id;
    private MemberResponseDto member;
    private String videoUrl;
    private String thumbnailUrl;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long viewCount;
    private Double voteRatio;
    private HashMap<Long, Long> votes;

    public static PostMainResponse entityToResponse(Post post) {
        return PostMainResponse.builder()
                .id(post.getId())
                .member(MemberConverter.toResponseDto(post.getMember()))
                .videoUrl(post.getVideoUrl())
                .thumbnailUrl(post.getThumbnailUrl())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .votes(post.getVotes())
                .build();
    }
}

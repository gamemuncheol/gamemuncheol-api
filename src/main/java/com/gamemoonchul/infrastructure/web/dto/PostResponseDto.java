package com.gamemoonchul.infrastructure.web.dto;

import com.gamemoonchul.domain.converter.MemberConverter;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private MemberResponseDto member;
    private String videoUrl;
    private String thumbnailUrl;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long viewCount;
    private HashMap<MatchUser, Integer> voteRate;

    public static PostResponseDto entityToResponse(Post entity, HashMap<MatchUser, Integer> voteRate) {
        return PostResponseDto.builder()
                .id(entity.getId())
                .member(MemberConverter.toResponseDto(entity.getMember()))
                .videoUrl(entity.getVideoUrl())
                .thumbnailUrl(entity.getThumbnailUrl())
                .title(entity.getTitle())
                .viewCount(entity.getViewCount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .voteRate(voteRate)
                .build();
    }
}

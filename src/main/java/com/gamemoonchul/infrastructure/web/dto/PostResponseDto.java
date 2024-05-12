package com.gamemoonchul.infrastructure.web.dto;

import com.gamemoonchul.domain.converter.MemberConverter;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.model.dto.VoteRate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private MemberResponseDto member;
    private String videoUrl;
    private String thumbnailUrl;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long viewCount;
    private List<VoteRate> voteRates;

    public static PostResponseDto entityToResponse(Post entity, List<VoteRate> voteRates) {
        return PostResponseDto.builder()
                .id(entity.getId())
                .member(MemberConverter.toResponseDto(entity.getMember()))
                .videoUrl(entity.getVideoUrl())
                .thumbnailUrl(entity.getThumbnailUrl())
                .title(entity.getTitle())
                .content(entity.getContent())
                .viewCount(entity.getViewCount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .voteRates(voteRates)
                .build();
    }
}

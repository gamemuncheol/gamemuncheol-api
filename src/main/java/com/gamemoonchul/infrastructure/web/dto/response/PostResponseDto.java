package com.gamemoonchul.infrastructure.web.dto.response;

import com.gamemoonchul.application.member.MemberConverter;
import com.gamemoonchul.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
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
    private Double voteRatio;
    private List<VoteOptionDetail> voteOptionDetails;

    public static PostResponseDto entityToResponse(Post entity) {
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
                .voteRatio(entity.getVoteRatio())
                .voteOptionDetails(entity.getVoteOptions()
                        .stream()
                        .map(VoteOptionDetail::entityToResponse)
                        .toList()
                )
                .build();
    }
}

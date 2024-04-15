package com.gamemoonchul.infrastructure.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PostResponseDto {
    private Long id;
    private MemberResponseDto member;
    private String videoUrl;
    private String thumbnailUrl;
    private String title;
    private Long viewCount;
}

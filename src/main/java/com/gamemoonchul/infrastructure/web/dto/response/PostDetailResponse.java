package com.gamemoonchul.infrastructure.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostDetailResponse {
    private Long id;
    private MemberResponse author;
    private String videoUrl;
    private String thumbnailUrl;
    private String title;
    private String content;
    private String timesAgo;
    private Long viewCount;
    private Long commentCount;
    private List<CommentResponse> comments;
    private List<VoteRatioResponse> voteDetail;
}

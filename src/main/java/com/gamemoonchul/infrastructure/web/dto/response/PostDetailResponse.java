package com.gamemoonchul.infrastructure.web.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PostDetailResponse(
    Long id,
    MemberResponse author,
    String videoUrl,
    String thumbnailUrl,
    String title,
    String content,
    String timesAgo,
    Long viewCount,
    Long commentCount,
    List<CommentResponse> comments,
    List<VoteRatioResponse> voteDetail
) {

}

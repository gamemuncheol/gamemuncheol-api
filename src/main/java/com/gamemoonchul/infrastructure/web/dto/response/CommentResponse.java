package com.gamemoonchul.infrastructure.web.dto.response;

import lombok.Builder;

@Builder
public record CommentResponse(
    MemberResponse author,
    String content,
    String timesAgo
) {

}

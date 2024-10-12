package com.gamemoonchul.infrastructure.web.dto.response;

import lombok.Builder;

@Builder
public record VoteResponse(
    Long id,
    Long postId,
    Long voteOptionId
) {

}

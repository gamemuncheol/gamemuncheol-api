package com.gamemoonchul.infrastructure.web.dto.request;

import lombok.Builder;

@Builder
public record VoteCreateRequest(
        Long voteOptionId,
        Long postId
) {

}

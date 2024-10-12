package com.gamemoonchul.infrastructure.web.dto.response;

import lombok.Builder;

@Builder
public record VoteRatioResponse(
    MatchUserResponse matchUserResponse,
    Double voteRatio
) {

}

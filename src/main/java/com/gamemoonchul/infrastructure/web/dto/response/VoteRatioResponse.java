package com.gamemoonchul.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class VoteRatioResponse {
    private MatchGameResponse.MatchUserResponse matchUserResponse;
    private Double voteRatio;
}

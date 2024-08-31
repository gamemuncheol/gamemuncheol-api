package com.gamemoonchul.infrastructure.web.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteRatioResponse {
    private MatchGameResponse.MatchUserResponse matchUserResponse;
    private Double voteRatio;
}

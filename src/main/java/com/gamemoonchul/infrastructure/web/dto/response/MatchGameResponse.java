package com.gamemoonchul.infrastructure.web.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MatchGameResponse(
    String gameId,
    String gameCreation,
    long gameDuration,
    String gameMode,
    List<MatchUserResponse> matchUsers
) {
}

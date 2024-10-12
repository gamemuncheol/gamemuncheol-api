package com.gamemoonchul.infrastructure.web.dto.response;

import lombok.Builder;

@Builder
public record MatchUserResponse(
    Long id,
    String nickname,
    String championName,
    String championThumbnail,
    boolean win
) {
}

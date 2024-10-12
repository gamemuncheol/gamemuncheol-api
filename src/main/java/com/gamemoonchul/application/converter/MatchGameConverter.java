package com.gamemoonchul.application.converter;

import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.infrastructure.web.dto.response.MatchGameResponse;

public class MatchGameConverter {
    public static MatchGameResponse toResponse(MatchGame matchGame) {
        return MatchGameResponse.builder()
            .gameId(matchGame.getGameId())
            .gameCreation(matchGame.getGameCreation())
            .gameDuration(matchGame.getGameDuration())
            .gameMode(matchGame.getGameMode())
            .matchUsers(matchGame.getMatchUsers()
                .stream()
                .map(MatchUserConverter::toResponse)
                .toList())
            .build();
    }

}

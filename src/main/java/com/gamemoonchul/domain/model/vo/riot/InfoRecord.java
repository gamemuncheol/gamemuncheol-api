package com.gamemoonchul.domain.model.vo.riot;

import lombok.*;

import java.util.List;

@Builder
public record InfoRecord(
        long gameCreation,
        long gameDuration,
        long gameEndTimestamp,
        long gameId,
        String gameMode,
        String gameName,
        long gameStartTimestamp,
        String gameType,
        String gameVersion,
        int mapId,
        List<ParticipantRecord> participants,
        List<TeamRecord> teams
) {
}
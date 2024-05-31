package com.gamemoonchul.domain.model.vo.riot;

import java.util.List;

public class InfoDummy {
    public static InfoRecord create() {
        return InfoRecord.builder()
                .gameCreation(1)
                .gameDuration(1)
                .gameEndTimestamp(1)
                .gameId(1)
                .gameMode("1")
                .gameName("1")
                .gameStartTimestamp(1)
                .gameType("1")
                .gameVersion("1")
                .mapId(1)
                .participants(List.of(ParticipantDummy.create()))
                .teams(List.of(TeamDummy.create())).build();

    }
}


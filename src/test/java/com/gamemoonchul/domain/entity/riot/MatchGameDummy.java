package com.gamemoonchul.domain.entity.riot;

import java.util.ArrayList;
import java.util.List;

public class MatchGameDummy {
    public static MatchGame create() {
        List<MatchUser> matchUsers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            matchUsers.add(MatchUserDummy.createDummy("1"));
        }
        return MatchGame.builder()
                .id(1)
                .gameCreation("2022-01-01 00:00:00")
                .gameDuration(1)
                .matchUsers(matchUsers)
                .gameMode("1")
                .build();
    }
}
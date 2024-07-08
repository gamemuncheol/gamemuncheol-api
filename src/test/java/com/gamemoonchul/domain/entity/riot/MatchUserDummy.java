package com.gamemoonchul.domain.entity.riot;

public class MatchUserDummy {
    public static MatchUser createDummy(String puuid) {
        return MatchUser.builder()
                .puuid(puuid)
                .nickname(puuid)
                .championName(puuid)
                .win(true)
                .build();
    }
}
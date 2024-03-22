package com.gamemoonchul.infrastructure.web.dto;

import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

@Getter
@Builder
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MatchGameResponse {
    private String gameId;
    private String gameCreation;
    private long gameDuration;
    private String gameMode;
    private List<MatchUserResponse> matchUsers;

    public static MatchGameResponse toResponse(MatchGame matchGame) {
        Properties properties = loadProperties(new Properties());
        return MatchGameResponse.builder()
                .gameId(matchGame.getGameId())
                .gameCreation(matchGame.getGameCreation())
                .gameDuration(matchGame.getGameDuration())
                .gameMode(matchGame.getGameMode())
                .matchUsers(matchGame.getMatchUsers().stream()
                        .map(matchUser
                                -> MatchUserResponse.toResponse(matchUser, properties))
                        .toList())
                .build();
    }

    private static Properties loadProperties(Properties properties) {
        try {
            InputStream inputStream = MatchUserResponse.class.getClassLoader().getResourceAsStream("lolchampion.properties");
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            properties = new Properties();
            properties.load(reader);
        } catch (IOException e) {
            log.error("properties 파일을 읽어오는데 실패했습니다.\n" + e.getMessage() + "\n" + e.getStackTrace());
        }
        return properties;
    }

    @Getter
    @Builder
    static class MatchUserResponse {
        private String puuid;
        private String nickname;
        private String championName;
        private String championThumbnail;
        private boolean win;

        public static MatchUserResponse toResponse(MatchUser matchUser, Properties properties) {
            String koChampName = getKoChampName(loadProperties(properties), matchUser);

            return MatchUserResponse.builder()
                    .puuid(matchUser.getPuuid())
                    .nickname(matchUser.getNickname())
                    .championName(koChampName)
                    .championThumbnail(matchUser.getChampionThumbnail())
                    .win(matchUser.isWin())
                    .build();
        }

        private static String getKoChampName(Properties properties, MatchUser matchUser) {
            String engChampName = matchUser.getChampionThumbnail().replace("https://ddragon.leagueoflegends.com/cdn/11.1.1/img/champion/", "").replace(".png", "");
            String koChampName;
            try {
                // 이후 properties를 사용하여 작업을 수행합니다.
                koChampName = (String) properties.get(engChampName);
            } catch (Exception e) {
                log.error("properties 파일을 읽어오는데 실패했습니다.\n" + e.getMessage() + "\n" + e.getStackTrace());
                koChampName = engChampName;
            }
            return koChampName;
        }
    }
}

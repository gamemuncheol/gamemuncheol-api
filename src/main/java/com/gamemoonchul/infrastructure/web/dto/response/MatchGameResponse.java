package com.gamemoonchul.infrastructure.web.dto.response;

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
        Properties properties = loadProperties();
        return MatchGameResponse.builder()
                .gameId(matchGame.getGameId())
                .gameCreation(matchGame.getGameCreation())
                .gameDuration(matchGame.getGameDuration())
                .gameMode(matchGame.getGameMode())
                .matchUsers(matchGame.getMatchUsers()
                        .stream()
                        .map(MatchUserResponse::toResponse)
                        .toList())
                .build();
    }

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = MatchUserResponse.class.getClassLoader()
                    .getResourceAsStream("lolchampion.properties");
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
        private Long id;
        private String nickname;
        private String championName;
        private String championThumbnail;
        private boolean win;

        public static MatchUserResponse toResponse(MatchUser matchUser) {
            String koChampName = getKoChampName(loadProperties(), matchUser);

            return MatchUserResponse.builder()
                    .id(matchUser.getId())
                    .nickname(matchUser.getNickname())
                    .championName(koChampName)
                    .championThumbnail(getChampThumbnail(matchUser.getChampionName()))
                    .win(matchUser.isWin())
                    .build();
        }

        public static String getChampThumbnail(String championName) {
            return "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/" + championName + "_0.jpg";
        }

        private static String getKoChampName(Properties properties, MatchUser matchUser) {
            String engChampName = matchUser.getChampionName();
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

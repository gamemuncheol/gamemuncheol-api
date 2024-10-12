package com.gamemoonchul.application.converter;

import com.gamemoonchul.common.util.PropertyUtil;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.infrastructure.web.dto.response.MatchUserResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class MatchUserConverter {
    public static MatchUserResponse toResponse(MatchUser matchUser) {
        String koChampName = getKoChampName(PropertyUtil.loadProperties(), matchUser);

        return MatchUserResponse.builder()
            .id(matchUser.getId())
            .nickname(matchUser.getNickname())
            .championName(koChampName)
            .championThumbnail(getChampThumbnail(matchUser.getChampionName()))
            .win(matchUser.isWin())
            .build();
    }

    /**
     * @param matchUser "VoteOption"의 "MatchUser"
     * @param voId      "VoteOption의 ID"
     */
    public static MatchUserResponse toResponseVoId(MatchUser matchUser, Long voId) {
        String koChampName = getKoChampName(PropertyUtil.loadProperties(), matchUser);

        return MatchUserResponse.builder()
            .id(voId)
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

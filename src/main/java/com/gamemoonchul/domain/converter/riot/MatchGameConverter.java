package com.gamemoonchul.domain.converter.riot;

import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.model.vo.riot.MatchVO;
import com.google.api.client.util.DateTime;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class MatchGameConverter {

    public MatchGame toMatchGame(MatchVO matchVO) {
        MatchGame entity = MatchGame.builder()
                .id(matchVO.getMetadata().getMatchId())
                .gameDuration(matchVO.getInfo().getGameDuration())
                .gameMode(matchVO.getInfo().getGameMode())
                .gameCreation(convertUnixToUtcTime(matchVO.getInfo().getGameCreation())).build();

        return entity;
    }

    public String convertUnixToUtcTime(long unixTimestamp) {
        Instant instant = Instant.ofEpochMilli(unixTimestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("UTC"));
        String utcTime = formatter.format(instant);
        return utcTime;
    }
//    private String getKda(ParticipantVO participantVO) {
//        return participantVO.getKills() + "/" + participantVO.getDeaths() + "/" + participantVO.getAssists();
//    }

//    private String getRating(ParticipantVO participantVO) {
//        double sum = (double) (participantVO.getKills() + participantVO.getAssists()) / participantVO.getDeaths();
//        return String.valueOf(sum);
//    }

//    private static String getNeutralMinionsKilled(MatchVO matchVO, ParticipantVO participantVO) {
//        return participantVO.getNeutralMinionsKilled() + " (" + participantVO.getNeutralMinionsKilled() / (matchVO.getInfo().getGameDuration() / 60) + ")";
//    }
//
//    private String getImgUrl(int itemId) {
//        return "https://ddragon.leagueoflegends.com/cdn/14.4.1/img/item/" + itemId + ".png";
//    }
}

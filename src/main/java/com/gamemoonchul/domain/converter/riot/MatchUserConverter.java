package com.gamemoonchul.domain.converter.riot;

import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.model.vo.riot.ParticipantVO;
import org.springframework.stereotype.Service;


@Service
public class MatchUserConverter {
    public MatchUser toEntities(ParticipantVO participant, MatchGame matchGame) {
        MatchUser entity = MatchUser.builder()
                .puuid(participant.getPuuid())
                .win(participant.isWin())
                .nickname(getNickname(participant))
                .championThumbnail(getChampionThumbnail(participant.getChampionName()))
                .build();
        entity.setMatchGame(matchGame);
        return entity;
    }

    private String getNickname(ParticipantVO participantVO) {
        return participantVO.getSummonerName() + " #" + participantVO.getRiotIdTagline();
    }

    private String getChampionThumbnail(String championName) {
        return "https://ddragon.leagueoflegends.com/cdn/11.1.1/img/champion/" + championName + ".png";
    }

}

package com.gamemoonchul.domain.converter.riot;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.model.vo.riot.MatchDummy;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.domain.model.vo.riot.ParticipantRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchUserConverterTest extends TestDataBase {

    @Test
    @DisplayName("MatchUserConverter 객체 생성 테스트")
    void createMatchUserConverter() {
        // given
        MatchRecord matchVO = MatchDummy.create();
        ParticipantRecord participantVO = matchVO.info().participants().get(0);
        MatchGame matchGame = new MatchGame(matchVO);

        // when
        MatchUser matchUser = new MatchUser(participantVO, matchGame);

        // then
        assertEquals(participantVO.puuid(), matchUser.getPuuid());
        assertEquals(participantVO.win(), matchUser.isWin());
        assertEquals(participantVO.summonerName() + " #" + participantVO.riotIdTagline(), matchUser.getNickname());
    }
}

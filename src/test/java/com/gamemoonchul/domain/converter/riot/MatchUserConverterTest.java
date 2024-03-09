package com.gamemoonchul.domain.converter.riot;

import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.model.vo.riot.MatchVO;
import com.gamemoonchul.domain.model.vo.riot.ParticipantVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MatchUserConverterTest {
    @Autowired
    private MatchUserConverter matchUserConverter;

    @Autowired
    private MatchGameConverter matchConverter;
    @Test
    @DisplayName("MatchUserConverter 객체 생성 테스트")
    void createMatchUserConverter() {
        // given
        MatchVO matchVO = MatchVO.Dummy.createDummy();
        ParticipantVO participantVO = matchVO.getInfo().getParticipants().get(0);
        MatchGame matchGame = matchConverter.toMatchGame(matchVO);

        // when
        MatchUser matchUser = matchUserConverter.toEntities(participantVO, matchGame);

        // then
        assertEquals(participantVO.getPuuid(), matchUser.getPuuid());
        assertEquals(participantVO.isWin(), matchUser.isWin());
        assertEquals(participantVO.getSummonerName() + " #" + participantVO.getRiotIdTagline(), matchUser.getNickname());
    }
}
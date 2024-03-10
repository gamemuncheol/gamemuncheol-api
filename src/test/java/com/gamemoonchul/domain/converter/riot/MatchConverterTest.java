package com.gamemoonchul.domain.converter.riot;

import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.model.vo.riot.MatchDummy;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MatchConverterTest {

    @Autowired
    private MatchGameConverter matchConverter;

    @Test
    void convertMatchGameToMatchVO() {
        // given
        MatchRecord matchVO = MatchDummy.create();

        // when
        MatchGame matchGame = matchConverter.toMatchGame(matchVO);

        // then
        assertEquals(matchVO.metadata().matchId(), matchGame.getId());
        assertEquals(matchVO.info().gameDuration(), matchGame.getGameDuration());
        assertEquals(matchVO.info().gameMode(), matchGame.getGameMode());
    }
}
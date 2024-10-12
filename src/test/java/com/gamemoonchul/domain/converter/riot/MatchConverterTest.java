package com.gamemoonchul.domain.converter.riot;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.model.vo.riot.MatchDummy;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchConverterTest extends TestDataBase {

    @Test
    void convertMatchGameToMatchVO() {
        // given
        MatchRecord matchVO = MatchDummy.create();

        // when
        MatchGame matchGame = new MatchGame(matchVO);

        // then
        assertEquals(matchVO.metadata().matchId(), matchGame.getGameId());
        assertEquals(matchVO.info().gameDuration(), matchGame.getGameDuration());
        assertEquals(matchVO.info().gameMode(), matchGame.getGameMode());
    }
}

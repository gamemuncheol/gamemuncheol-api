package com.gamemoonchul.domain.converter.riot;

import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.model.vo.riot.MatchVO;
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
        MatchVO matchVO = MatchVO.Dummy.createDummy();

        // when
        MatchGame matchGame = matchConverter.toMatchGame(matchVO);

        // then
        assertEquals(matchVO.getMetadata().getMatchId(), matchGame.getId());
        assertEquals(matchVO.getInfo().getGameDuration(), matchGame.getGameDuration());
        assertEquals(matchVO.getInfo().getGameMode(), matchGame.getGameMode());
    }
}
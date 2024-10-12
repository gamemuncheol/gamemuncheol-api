package com.gamemoonchul.infrastructure.web.dto;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.application.converter.MatchGameConverter;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchGameDummy;
import com.gamemoonchul.infrastructure.web.dto.response.MatchGameResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchGameResponseTest extends TestDataBase {
    @DisplayName("MatchGameResponse 객체 생성 테스트")
    @Test
    void createMatchGameResponse() {
        // given
        MatchGame matchGame = MatchGameDummy.create();

        // when
        MatchGameResponse matchGameResponse = MatchGameConverter.toResponse(matchGame);

        // then
        assertEquals(matchGame.getGameCreation(), matchGameResponse.gameCreation());
        assertEquals(matchGame.getGameMode(), matchGameResponse.gameMode());
        assertEquals(matchGame.getGameDuration(), matchGameResponse.gameDuration());
        assertEquals(matchGame.getMatchUsers()
            .size(), matchGameResponse.matchUsers()
            .size());
    }

}

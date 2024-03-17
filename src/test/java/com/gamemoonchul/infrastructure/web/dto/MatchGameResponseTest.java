package com.gamemoonchul.infrastructure.web.dto;

import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchGameDummy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MatchGameResponseTest {
    @DisplayName("MatchGameResponse 객체 생성 테스트")
    @Test
    void createMatchGameResponse() {
        // given
        MatchGame matchGame = MatchGameDummy.create();

        // when
        MatchGameResponse matchGameResponse = MatchGameResponse.toResponse(matchGame);

        // then
        assertEquals(matchGame.getGameCreation(), matchGameResponse.getGameCreation());
        assertEquals(matchGame.getGameMode(), matchGameResponse.getGameMode());
        assertEquals(matchGame.getGameDuration(), matchGameResponse.getGameDuration());
        assertEquals(matchGame.getMatchUsers().size(), matchGameResponse.getMatchUsers().size());
    }

}
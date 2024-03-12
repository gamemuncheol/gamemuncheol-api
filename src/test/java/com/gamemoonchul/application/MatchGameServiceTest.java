package com.gamemoonchul.application;

import com.gamemoonchul.MatchUserService;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.model.vo.riot.MatchDummy;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MatchGameServiceTest {
    @Autowired
    private MatchGameService matchGameService;

    @Autowired
    private MatchUserService matchUserService;

    @DisplayName("저장테스트")
    @Test
    void save() {
        // given
        MatchRecord dummyVO = MatchDummy.create();

        // when
        MatchGame matchGame = matchGameService.save(dummyVO);

        // when
        assertEquals(dummyVO.metadata().matchId(), matchGame.getId());
    }

    @DisplayName("조회테스트, matchUsers Loading Eager")
    @Test
    void setMatchGameService() {
        // given
        MatchRecord dummyVO = MatchDummy.create();
        MatchGame matchGame = matchGameService.save(dummyVO);
        List<MatchUser> matchUsers = matchUserService.saveAll(dummyVO.info().participants(), matchGame);

        // when
        Optional<MatchGame> optionalMatchGame = matchGameService.findById(matchGame.getId());
        MatchGame result = optionalMatchGame.get();

        // then
        assertEquals(matchGame.getId(), result.getId());
        assertEquals(matchGame.getMatchUsers().size(), result.getMatchUsers().size());
    }
}
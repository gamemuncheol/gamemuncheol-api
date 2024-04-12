package com.gamemoonchul;

import com.gamemoonchul.application.MatchGameService;
import com.gamemoonchul.application.MatchUserService;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.model.vo.riot.MatchDummy;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.domain.model.vo.riot.ParticipantRecord;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MatchUserServiceTest {
    @Autowired
    private MatchGameService matchGameService;
    @Autowired
    private MatchUserService matchUserService;
    @Test
    @DisplayName("MatchUser 생성 테스트, MatchGame Eager Loading 테스트")
    void createMatchUser() {
        // given
        MatchRecord gameVO = MatchDummy.create();
        MatchGame gameEntity = matchGameService.save(gameVO);
        List<ParticipantRecord> participants = gameVO.info().participants();


        // when
        matchUserService.saveAll(participants, gameEntity);
        List<MatchUser> matchUsers = matchUserService.findByMatchGameId(gameEntity);

        // then
        assertEquals(participants.size(), matchUsers.size());
        assertEquals(matchUsers.get(0).getMatchGame().getGameId(), gameEntity.getGameId());
    }
}
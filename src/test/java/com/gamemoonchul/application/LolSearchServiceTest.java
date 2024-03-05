package com.gamemoonchul.application;

import com.gamemoonchul.domain.model.vo.riot.AccountVO;
import com.gamemoonchul.domain.model.vo.riot.MatchVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LolSearchServiceTest {
    @Autowired
    LolSearchService lolSearchService;

    @Test
    @DisplayName("롤 유저 검색 테스트")
    void searchUser() {
        // given
        String gameName = "hide on bush";
        String tagLine = "kr1";

        // when
        AccountVO accountVO = lolSearchService.searchUser(gameName, tagLine);

        // then
        assertEquals("2u_YSGly2rGy9LwxQ-uAjZ0gRg6WWfIzRxDXW2OALJJIyaWusYh8JpybeSCPZVddQUTE9w2JMh-bXQ", accountVO.getPuuid());
    }

    @Test
    @DisplayName("게임 아이디로 게임 검색 테스트")
    void searchMatche() {
        // given
        String matchId = "KR_6862565824";

        // when
        MatchVO result = lolSearchService.searchMatch(matchId);

        // then
        assertEquals(matchId, result.getMetadata().getMatchId());
    }
}
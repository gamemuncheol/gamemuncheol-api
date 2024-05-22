package com.gamemoonchul.infrastructure.adapter;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.application.ports.output.RiotApiPort;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.domain.status.SearchStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class RiotApiAdapterTest extends TestDataBase {
    @Autowired
    RiotApiPort riotApi;

//    @Test
//    @DisplayName("롤 유저 검색 테스트")
//    void searchUser() {
//        // given
//        String gameName = "hide on bush";
//        String tagLine = "kr1";
//
//        // when
//        AccountRecord accountVO = lolSearchService.searchUser(gameName, tagLine);
//
//        // then
//        assertEquals("2u_YSGly2rGy9LwxQ-uAjZ0gRg6WWfIzRxDXW2OALJJIyaWusYh8JpybeSCPZVddQUTE9w2JMh-bXQ", accountVO.puuid());
//    }

    @Test
    @DisplayName("게임 아이디로 게임 검색 테스트")
    void searchMatche() {
        // given
        String matchId = "KR_6995213153";

        // when
        MatchRecord result = riotApi.searchMatch(matchId);

        // then
        assertEquals(matchId, result.metadata().matchId());
    }


    @Test
    @DisplayName("찾을 수 없는 전적정보 요청시 NotFoundException 발생")
    void searchNotMatched() {
        // given
        String matchId = "132132313";

        // when // then
        assertThrows(BadRequestException.class, () -> {
            riotApi.searchMatch(matchId);
        }, SearchStatus.SEARCH_RESULT_NOT_FOUND.getMessage());
    }
}
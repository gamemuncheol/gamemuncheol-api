package com.gamemoonchul.application;

import com.gamemoonchul.MatchUserService;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.model.vo.riot.MatchVO;
import com.gamemoonchul.domain.model.vo.riot.ParticipantVO;
import com.gamemoonchul.infrastructure.adapter.LolSearchAdapter;
import com.gamemoonchul.infrastructure.web.dto.MatchGameResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class BoardServiceTest {
    private LolSearchAdapter mockLolSearch = mock(LolSearchAdapter.class);

    @Autowired
    private MatchGameService matchGameService;

    @Autowired
    private MatchUserService matchUserService;

    @Autowired
    private LolSearchAdapter lolSearchAdapter;

    private BoardService boardService;

    @Test
    @DisplayName("이미 저장된 게임이 있을 때 lolSearchAdapter를 통해서 검색하지 않는다")
    void searchAlreadySavedGame() {
        // given
        MatchVO vo = MatchVO.Dummy.createDummy();
        MatchGame game = matchGameService.save(vo);
        boardService = new BoardService(matchGameService, matchUserService, mockLolSearch);
        List<ParticipantVO> participantVO = vo.getInfo().getParticipants();
        matchUserService.saveAll(participantVO, game);

        // when
        boardService.searchMatch(game.getId());

        // then
        verify(mockLolSearch, never()).searchMatch(anyString());
    }

    @Test
    @DisplayName("저장되지 않은 게임이 있을 때 lolSearchAdapter를 통해서 검색한다")
    void searchNotSavedGame() {
        // given
        boardService = new BoardService(matchGameService, matchUserService, lolSearchAdapter);
        String gameId = "6980800844";

        // when
        MatchGameResponse response = boardService.searchMatch(gameId);

        // then
        assertEquals(response.getGameId(), "KR_" + gameId);
        assertEquals(response.getMatchUsers().size(), 10);
    }
}
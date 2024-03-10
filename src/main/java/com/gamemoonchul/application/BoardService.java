package com.gamemoonchul.application;

import com.gamemoonchul.MatchUserService;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.model.vo.riot.MatchVO;
import com.gamemoonchul.domain.model.vo.riot.ParticipantVO;
import com.gamemoonchul.infrastructure.adapter.LolSearchAdapter;
import com.gamemoonchul.infrastructure.web.dto.MatchGameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MatchGameService matchGameService;
    private final MatchUserService matchUserService;
    private final LolSearchAdapter lolSearchAdapter;

    public MatchGameResponse searchMatch(String gameId) {
        Optional<MatchGame> optionalEntity = matchGameService.findById(gameId);
        MatchGame matchGame;


        if (optionalEntity.isPresent()) {
            matchGame = optionalEntity.get();
        } else {
            MatchVO vo = lolSearchAdapter.searchMatch(gameId);
            matchGame = matchGameService.save(vo);
            matchUserService.saveAll(vo.getInfo().getParticipants(), matchGame);
        }
        MatchGameResponse response = MatchGameResponse.toResponse(matchGame);
        return response;
    }
}

package com.gamemoonchul.application;

import com.gamemoonchul.MatchUserService;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.infrastructure.adapter.LolSearchAdapter;
import com.gamemoonchul.infrastructure.web.dto.MatchGameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            MatchRecord vo = lolSearchAdapter.searchMatch(gameId);
            matchGame = matchGameService.save(vo);
            matchUserService.saveAll(vo.info().participants(), matchGame);
        }
        MatchGameResponse response = MatchGameResponse.toResponse(matchGame);
        return response;
    }
}

package com.gamemoonchul.application;

import com.gamemoonchul.application.ports.output.RiotApiPort;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.infrastructure.web.dto.MatchGameResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class RiotApiService {
    private final MatchGameService matchGameService;
    private final MatchUserService matchUserService;
    private final RiotApiPort riotApi;

            public MatchGameResponse searchMatch(String gameId) {
        Optional<MatchGame> savedEntity = matchGameService.findByGameId(gameId);
        MatchGame matchGame;


        if (savedEntity != null && savedEntity.isPresent()) {
            matchGame = savedEntity.get();
        } else {
            MatchRecord vo = riotApi.searchMatch(gameId);
            matchGame = matchGameService.save(vo);
            matchUserService.saveAll(vo.info().participants(), matchGame);
        }
        MatchGameResponse response = MatchGameResponse.toResponse(matchGame);
        return response;
    }

}

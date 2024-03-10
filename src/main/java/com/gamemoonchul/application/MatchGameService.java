package com.gamemoonchul.application;

import com.gamemoonchul.domain.converter.riot.MatchGameConverter;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.infrastructure.repository.MatchGameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchGameService {
    private final MatchGameRepository matchGameRepository;
    private final MatchGameConverter matchConverter;

    public Optional<MatchGame> findById(String gameId) {
        return matchGameRepository.findById(gameId);
    }

    public MatchGame save(MatchRecord vo) {
        MatchGame matchGame = matchConverter.toMatchGame(vo);
        return matchGameRepository.save(matchGame);
    }
}
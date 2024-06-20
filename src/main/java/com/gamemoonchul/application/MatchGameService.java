package com.gamemoonchul.application;

import com.gamemoonchul.application.converter.riot.MatchGameConverter;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.infrastructure.repository.MatchGameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MatchGameService {
    private final MatchGameRepository matchGameRepository;
    private final MatchGameConverter matchConverter;

    public Optional<MatchGame> findByGameId(String gameId) {
        return matchGameRepository.findByGameId(gameId);
    }

    public MatchGame save(MatchRecord vo) {
        MatchGame matchGame = matchConverter.toMatchGame(vo);
        return matchGameRepository.save(matchGame);
    }
}
package com.gamemoonchul.application;

import com.gamemoonchul.domain.converter.riot.MatchGameConverter;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.model.vo.riot.MatchVO;
import com.gamemoonchul.infrastructure.repository.MatchGameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchGameService {
    private final MatchGameRepository matchGameRepository;
    private final MatchGameConverter matchConverter;

    private MatchGame save(MatchVO vo) {
        MatchGame matchGame = matchConverter.toMatchGame(vo);
        return matchGameRepository.save(matchGame);
    }
}
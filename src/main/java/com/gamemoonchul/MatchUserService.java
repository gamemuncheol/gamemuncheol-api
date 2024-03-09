package com.gamemoonchul;

import com.gamemoonchul.domain.converter.riot.MatchUserConverter;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.model.vo.riot.ParticipantVO;
import com.gamemoonchul.infrastructure.repository.MatchUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchUserService {
    private final MatchUserRepository matchUserRepository;
    private final MatchUserConverter matchUserConverter;

    public void saveAll(List<ParticipantVO> participants, MatchGame matchGame) {
        participants.stream().map(
                participant -> matchUserConverter.toEntities(participant, matchGame)
        ).forEach(matchUserRepository::save);
    }

    public List<MatchUser> findByMatchGameId(String gameId) {
        return matchUserRepository.findByMatchGame_Id(gameId);
    }
}

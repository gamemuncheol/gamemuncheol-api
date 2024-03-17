package com.gamemoonchul;

import com.gamemoonchul.domain.converter.riot.MatchUserConverter;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.model.vo.riot.ParticipantRecord;
import com.gamemoonchul.infrastructure.repository.MatchUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchUserService {
    private final MatchUserRepository matchUserRepository;
    private final MatchUserConverter matchUserConverter;

    public List<MatchUser> saveAll(List<ParticipantRecord> participants, MatchGame matchGame) {
        List<MatchUser> matchUsers = new ArrayList<>();
        participants.stream().map(
                participant -> matchUserConverter.toEntities(participant, matchGame)
        ).forEach(matchUser -> {
            matchUsers.add(matchUserRepository.save(matchUser));
        });
        return matchUsers;
    }

    public List<MatchUser> findByMatchGameId(MatchGame matchGame) {
        return matchUserRepository.findByMatchGame(matchGame);
    }
}

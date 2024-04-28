package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.converter.riot.MatchUserConverter;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.model.vo.riot.ParticipantRecord;
import com.gamemoonchul.domain.status.PostStatus;
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
    private final MatchUt sserConverter matchUserConverter;

    public List<MatchUser> saveAll(List<ParticipantRecord> participants, MatchGame matchGame) {
        List<MatchUser> matchUsers = new ArrayList<>();
        participants.stream()
                .map(
                        participant -> matchUserConverter.toEntities(participant, matchGame)
                )
                .forEach(matchUser -> {
                    matchUsers.add(matchUserRepository.save(matchUser));
                });
        return matchUsers;
    }

    public List<MatchUser> findByMatchGameId(MatchGame matchGame) {
        return matchUserRepository.findByMatchGame(matchGame);
    }

    public MatchUser findById(Long id) {
        return matchUserRepository.findTopById(id)
                .orElseThrow(() -> new ApiException(PostStatus.WRONG_MATCH_USER));
    }
}

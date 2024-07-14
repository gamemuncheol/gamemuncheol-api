package com.gamemoonchul.application;

import com.gamemoonchul.application.converter.riot.MatchUserConverter;
import com.gamemoonchul.common.exception.NotFoundException;
import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.model.vo.riot.ParticipantRecord;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.MatchUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MatchUserService {
    private final MatchUserRepository matchUserRepository;
    private final MatchUserConverter matchUserConverter;

    public List<MatchUser> saveAll(List<ParticipantRecord> participants, MatchGame matchGame) {
        List<MatchUser> matchUsers = new ArrayList<>();
        participants.stream().map(participant -> matchUserConverter.toEntities(participant, matchGame)).forEach(matchUser -> {
            matchUsers.add(matchUserRepository.save(matchUser));
        });
        return matchUsers;
    }

    public MatchUser findById(Long id) {
        return matchUserRepository.findById(id).orElseThrow(() -> new NotFoundException(PostStatus.WRONG_MATCH_USER));
    }

    public List<MatchUser> findByMatchGameId(Long gameId) {
        return matchUserRepository.searchByGameId(gameId);
    }
}

package com.gamemoonchul.infrastructure.repository.ifs;

import com.gamemoonchul.domain.entity.riot.MatchUser;

import java.util.List;

public interface MatchUserRepositoryIfs {
    List<MatchUser> searchByGameId(Long gameId);
}
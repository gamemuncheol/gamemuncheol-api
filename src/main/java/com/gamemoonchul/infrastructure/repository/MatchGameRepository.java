package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.riot.MatchGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchGameRepository extends JpaRepository<MatchGame, Long>{
    Optional<MatchGame> findByGameId(String gameId);
}

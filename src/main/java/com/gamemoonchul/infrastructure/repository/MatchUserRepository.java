package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchUserRepository extends JpaRepository<MatchUser, Long> {
}

package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.riot.MatchUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchUserRepository extends JpaRepository<MatchUser, Long> {
    List<MatchUser> findByMatchGame_Id(String gameId);

}

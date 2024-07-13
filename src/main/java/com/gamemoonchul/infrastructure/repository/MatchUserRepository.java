package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.infrastructure.repository.ifs.MatchUserRepositoryIfs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchUserRepository extends JpaRepository<MatchUser, Long>, MatchUserRepositoryIfs {

    Optional<MatchUser> findById(Long id);
}

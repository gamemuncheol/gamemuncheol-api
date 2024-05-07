package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long>  {
}

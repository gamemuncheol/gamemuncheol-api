package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.VoteOptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteOptionRepository extends JpaRepository<VoteOptions, Long> {
    public List<VoteOptions> findByPost_Id(Long id);
}



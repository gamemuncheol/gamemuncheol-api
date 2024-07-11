package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.infrastructure.repository.ifs.VoteOptionRepositoryIfs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteOptionRepository extends JpaRepository<VoteOptions, Long>, VoteOptionRepositoryIfs {

    Optional<VoteOptions> findByIdAndPostId(Long id, Long postId);
}



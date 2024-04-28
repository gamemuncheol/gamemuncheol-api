package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.infrastructure.repository.ifs.VoteOptionRepositoryIfs;
import com.gamemoonchul.infrastructure.repository.impl.VoteOptionRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteOptionRepository extends JpaRepository<VoteOptions, Long>, VoteOptionRepositoryIfs {
}



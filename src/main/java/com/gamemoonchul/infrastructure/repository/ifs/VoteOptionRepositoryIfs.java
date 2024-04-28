package com.gamemoonchul.infrastructure.repository.ifs;

import com.gamemoonchul.domain.entity.VoteOptions;

import java.util.List;

public interface VoteOptionRepositoryIfs {
    List<VoteOptions> searchByPostId(Long postId);
}

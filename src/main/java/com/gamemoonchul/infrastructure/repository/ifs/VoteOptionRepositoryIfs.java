package com.gamemoonchul.infrastructure.repository.ifs;

import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.domain.model.dto.VoteRate;

import java.util.List;

public interface VoteOptionRepositoryIfs {
    List<VoteOptions> searchByPostId(Long postId);

    List<VoteRate> getVoteRateByPostId(Long postId);
}

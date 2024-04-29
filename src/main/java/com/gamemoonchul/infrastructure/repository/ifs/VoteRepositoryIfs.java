package com.gamemoonchul.infrastructure.repository.ifs;

import com.gamemoonchul.domain.entity.Vote;
import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.domain.entity.riot.MatchUser;

import java.util.HashMap;
import java.util.Optional;

public interface VoteRepositoryIfs {
    Optional<Vote> searchVoteByPostIdAndVoteUserId(Long postId, Long voteUserId);
    HashMap<MatchUser, Integer> getVoteRateByPostId(Long postId);
}

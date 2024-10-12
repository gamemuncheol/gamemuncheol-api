package com.gamemoonchul.application.converter;

import com.gamemoonchul.domain.entity.Vote;
import com.gamemoonchul.infrastructure.web.dto.response.VoteResponse;

public class VoteConverter {
    public static VoteResponse entityToResponse(Vote vote) {
        return VoteResponse.builder()
            .id(vote.getId())
            .postId(vote.getPost().getId())
            .voteOptionId(vote.getVoteOption().getId())
            .build();
    }
}

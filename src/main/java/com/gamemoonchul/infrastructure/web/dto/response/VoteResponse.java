package com.gamemoonchul.infrastructure.web.dto.response;

import com.gamemoonchul.domain.entity.Vote;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteResponse {
    private Long id;
    private Long postId;
    private Long voteOptionId;

    public static VoteResponse entityToResponse(Vote vote) {
        return VoteResponse.builder()
                .id(vote.getId())
                .postId(vote.getPost().getId())
                .voteOptionId(vote.getVoteOption().getId())
                .build();
    }
}

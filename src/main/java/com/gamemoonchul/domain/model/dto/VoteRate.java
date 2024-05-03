package com.gamemoonchul.domain.model.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
public class VoteRate {
    private Long matchUserId;
    private String nickname;
    private String championThumbnail;
    private Long voteOptionsId;
    private Long rate;

    @QueryProjection
    public VoteRate(
            Long matchUserId,
            String nickname,
            String championThumbnail,
            Long voteOptionsId,
            Long voteCount
    ) {
        this.matchUserId = matchUserId;
        this.nickname = nickname;
        this.championThumbnail = championThumbnail;
        this.voteOptionsId = voteOptionsId;
        this.rate = voteCount;
    }
}

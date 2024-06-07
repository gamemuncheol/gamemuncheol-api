package com.gamemoonchul.domain.model.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class VoteRate {
    private Long matchUserId;
    private String nickname;
    private String championThumbnail;
    private Long voteOptionsId;
    private Double ratio;

    @QueryProjection
    public VoteRate(
            Long matchUserId,
            String nickname,
            String championThumbnail,
            Long voteOptionsId,
            Long ratio
    ) {
        this.matchUserId = matchUserId;
        this.nickname = nickname;
        this.championThumbnail = championThumbnail;
        this.voteOptionsId = voteOptionsId;
        this.ratio = ratio == null ? 0. : ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }
}

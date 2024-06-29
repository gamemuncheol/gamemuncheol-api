package com.gamemoonchul.infrastructure.web.dto.response;

import com.gamemoonchul.domain.entity.VoteOptions;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteOptionDetail {
    private Long matchUserId;
    private String nickname;
    private String championThumbnail;
    private Long voteOptionsId;
    private Double ratio;

    public static VoteOptionDetail entityToResponse(VoteOptions entity) {
        return VoteOptionDetail.builder()
                .matchUserId(entity.getMatchUser()
                        .getId())
                .nickname(entity.getMatchUser()
                        .getNickname())
                .championThumbnail(entity.getMatchUser()
                        .getChampionThumbnail())
                .voteOptionsId(entity.getId())
                .build();
    }
}

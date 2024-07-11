package com.gamemoonchul.domain.status;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VoteOptionStatus implements ApiStatusIfs {
    VOTE_OPTION_NOT_FOUND(7404, "투표 옵션을 찾을 수 없습니다.");

    private final Integer statusCode;
    private final String message;
}

package com.gamemoonchul.domain.model.vo.riot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
@AllArgsConstructor
public class TeamVO {
    private final int teamId;
    private final boolean win;
}

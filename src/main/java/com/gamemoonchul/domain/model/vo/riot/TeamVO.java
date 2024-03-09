package com.gamemoonchul.domain.model.vo.riot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
@AllArgsConstructor
public class TeamVO {
    private final int teamId;
    private final boolean win;

    public static class Dummy {
        public static TeamVO createDummy() {
            return TeamVO.builder()
                    .teamId(1)
                    .win(true)
                    .build();
        }
    }
}

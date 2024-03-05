package com.gamemoonchul.domain.model.vo.riot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter @Builder
public class AccountVO {
    private final String puuid;
    private final String gameName;
    private final String tagLine;
}

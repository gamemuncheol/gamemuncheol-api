package com.gamemoonchul.domain.model.vo.riot;

import lombok.*;

import java.util.List;

@Getter @Builder
@AllArgsConstructor
public class MetadataVO {
    private final String dataVersion;
    private final String matchId;
    private final List<String>  participants;
}

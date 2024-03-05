package com.gamemoonchul.domain.model.vo.riot;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class MatchVO {
    private final MetadataVO metadata;
    private final InfoVO info;
}

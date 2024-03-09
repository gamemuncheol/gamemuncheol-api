package com.gamemoonchul.domain.model.vo.riot;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class MatchVO {
    private final MetadataVO metadata;
    private final InfoVO info;

    public static class Dummy {
        public static MatchVO createDummy() {
            return MatchVO.builder()
                    .metadata(MetadataVO.Dummy.createDummy())
                    .info(InfoVO.Dummy.createDummy())
                    .build();
        }
    }
}

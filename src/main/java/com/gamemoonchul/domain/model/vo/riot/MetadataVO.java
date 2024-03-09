package com.gamemoonchul.domain.model.vo.riot;

import lombok.*;

import java.util.List;

@Getter @Builder
@AllArgsConstructor
public class MetadataVO {
    private final String dataVersion;
    private final String matchId;
    private final List<String>  participants;

    public static class Dummy {
        public static MetadataVO createDummy() {
            return MetadataVO.builder()
                    .dataVersion("1")
                    .matchId("1")
                    .participants(List.of("1", "2", "3"))
                    .build();
        }
    }
}

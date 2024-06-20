package com.gamemoonchul.domain.model.vo.riot;

import java.util.List;

public class MetadataDummy {
    public static MetadataRecord create() {
        return new MetadataRecord("1", "1", List.of("1", "2", "3"));
    }

    public static MetadataRecord create(String gameId) {
        return new MetadataRecord("1", gameId, List.of("1", "2", "3"));
    }

}

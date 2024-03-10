package com.gamemoonchul.domain.model.vo.riot;

import java.util.List;

public class MetadataDummy {
    public static MetadataRecord create() {
        return new MetadataRecord("1", "1", List.of("1", "2", "3"));
    }
}

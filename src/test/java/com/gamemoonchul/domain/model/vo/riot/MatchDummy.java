package com.gamemoonchul.domain.model.vo.riot;

public class MatchDummy  {
    public static MatchRecord create() {
        return new MatchRecord( MetadataDummy.create(), InfoDummy.create());
    }
}

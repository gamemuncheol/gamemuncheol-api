package com.gamemoonchul.application.ports.output;

import com.gamemoonchul.domain.model.vo.riot.MatchRecord;

public interface RiotApiPort {
    public MatchRecord searchMatch(String matchId);
}

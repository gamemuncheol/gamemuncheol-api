package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.RiotApiService;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.response.MatchGameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/api/riot")
public class RiotApiController {
    private final RiotApiService riotApiService;

    @GetMapping("/search-matches/{gameId}")
    public MatchGameResponse searchMatch(@PathVariable(name = "gameId") String gameId) {
        return riotApiService.searchMatch("KR_" + gameId);
    }
}

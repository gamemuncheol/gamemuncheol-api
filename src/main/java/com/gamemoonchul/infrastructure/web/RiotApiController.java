package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.RiotApiService;
import com.gamemoonchul.application.S3Service;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.MatchGameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/api/riot")
public class RiotApiController {
    private final RiotApiService riotApiService;

    @GetMapping("/search-match/{gameId}")
    public MatchGameResponse searchMatch(@PathVariable(name = "gameId") String gameId) {
        return riotApiService.searchMatch("KR_" + gameId);
    }
}

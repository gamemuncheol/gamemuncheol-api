package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.SearchService;
import com.gamemoonchul.domain.redisEntity.RedisCachedGame;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/api/search")
public class SearchApiController {
    private final SearchService searchService;

    @GetMapping("/lol/{keyword}")
    public List<RedisCachedGame> searchLol(
            @PathVariable
            String keyword) {
        return searchService.cachedSearch(keyword);
    }
}

package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.SearchService;
import com.gamemoonchul.domain.vo.SearchedGame;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/open-api/search")
public class SearchApiController {
    private final SearchService searchService;

    @GetMapping("/lol/{id}")
    public List<SearchedGame> searchLol(
            @RequestParam
            String keyword) {
        return searchService.search(keyword);
    }
}

package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.BoardService;
import com.gamemoonchul.infrastructure.adapter.LolSearchAdapter;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.MatchGameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/api/board")
public class BoardApiController {
    private final BoardService boardService;

    @GetMapping("/searchMatch/{gameId}")
    public MatchGameResponse searchMatch(@PathVariable String gameId) {
        return boardService.searchMatch(gameId);
    }
}

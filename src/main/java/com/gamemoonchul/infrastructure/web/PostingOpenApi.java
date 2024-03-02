package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.YoutubeService;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestControllerWithEnvelopPattern
@RequestMapping("/open-api/board")
@RequiredArgsConstructor
public class PostingOpenApi {

    private final YoutubeService youtubeService;

    @GetMapping("/test")
    public void test() {
        youtubeService.upload();
    }
}

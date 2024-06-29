package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.PostOpenApiService;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.response.PostMainPageResponse;
import com.gamemoonchul.infrastructure.web.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/open-api/posts")
@RestControllerWithEnvelopPattern
@RequiredArgsConstructor
public class PostOpenApiController {
    private final PostOpenApiService postService;

    @GetMapping("/page/new")
    public Pagination<PostMainPageResponse> getLatestPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return postService.getLatestPosts(page, size);
    }

    @GetMapping("/page/grill")
    public Pagination<PostMainPageResponse> getGrillPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return postService.getGrillPosts(page, size);
    }

    @GetMapping("/page/hot")
    public Pagination<PostMainPageResponse> getHotPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return postService.getHotPosts(page, size);
    }
}

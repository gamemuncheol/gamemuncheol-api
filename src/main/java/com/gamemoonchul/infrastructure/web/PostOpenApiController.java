package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.PostOpenApiService;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.PostMainPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@RequestMapping("/open-api/posts")
@RestControllerWithEnvelopPattern
@RequiredArgsConstructor
public class PostOpenApiController {
    private final PostOpenApiService postService;

    @GetMapping("/page/new")
    public Pagination<PostMainPageResponse> fetchByLatest(
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "id", required = false) Long id
    ) {
        Pageable pageable = PageRequest.of(0, size);
        return postService.fetchByLatest(id, pageable);
    }

    @GetMapping("/page/grill")
    public Pagination<PostMainPageResponse> fetchGrillPosts(
            @RequestParam(value = "voteRatio", required = false) Double voteRatio,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(0, size);
        return postService.getGrillPosts(voteRatio, pageable);
    }
}

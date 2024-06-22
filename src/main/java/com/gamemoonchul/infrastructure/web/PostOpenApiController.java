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
            @RequestParam(value = "cursor", required = false) LocalDateTime cursor
    ) {
        Pageable pageable = PageRequest.of(0, size);
        return postService.fetchByLatest(cursor, pageable);
    }

    @GetMapping("/page/grill")
    public Pagination<PostMainPageResponse> getGrillPosts(
            @RequestParam(value = "cursor", required = false) Double cursor,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(0, size);
        return postService.getGrillPosts(cursor, pageable);
    }
}

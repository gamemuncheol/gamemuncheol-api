package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.PostOpenApiService;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/open-api/post")
@RestControllerWithEnvelopPattern
@RequiredArgsConstructor
public class PostOpenApiController {
    private final PostOpenApiService postService;

    @GetMapping("/page/new")
    public Pagination<PostResponseDto> fetchByLatest(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return postService.fetchByLatest(pageable);
    }

    @GetMapping("")
    public List<Post> getHotPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return postService.getHotPosts(page, size);
    }
}

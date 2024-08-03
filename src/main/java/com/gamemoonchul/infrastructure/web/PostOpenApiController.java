package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.post.PostOpenApiService;
import com.gamemoonchul.common.annotation.MemberId;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.response.PostDetailResponse;
import com.gamemoonchul.infrastructure.web.dto.response.PostMainPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/open-api/posts")
@RestControllerWithEnvelopPattern
@RequiredArgsConstructor
public class PostOpenApiController {
    private final PostOpenApiService postService;

    @GetMapping
    public PostDetailResponse getPostDetail(
            @RequestParam(value = "id") Long id,
            @MemberId Long requestMemberId
    ) {
        PostDetailResponse response = postService.getPostDetails(id, requestMemberId);

        return response;
    }

    @GetMapping("/page/new")
    public Pagination<PostMainPageResponse> getLatestPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @MemberId Long requestMemberId
    ) {
        return postService.getLatestPosts(requestMemberId, page, size);
    }

    @GetMapping("/page/grill")
    public Pagination<PostMainPageResponse> getGrillPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @MemberId Long requestMemberId
    ) {
        return postService.getGrillPosts(requestMemberId, page, size);
    }

    @GetMapping("/page/hot")
    public Pagination<PostMainPageResponse> getHotPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @MemberId Long requestMemberId
    ) {
        return postService.getHotPosts(page, size, requestMemberId);
    }
}

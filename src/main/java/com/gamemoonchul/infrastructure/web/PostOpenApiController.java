package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.CommentService;
import com.gamemoonchul.application.PostOpenApiService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.response.CommentResponse;
import com.gamemoonchul.infrastructure.web.dto.response.PostDetailResponse;
import com.gamemoonchul.infrastructure.web.dto.response.PostMainPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/open-api/posts")
@RestControllerWithEnvelopPattern
@RequiredArgsConstructor
public class PostOpenApiController {
    private final PostOpenApiService postService;
    private final CommentService commentService;

    @GetMapping
    public PostDetailResponse getPostDetail(
            @RequestParam(value = "id") Long id,
            @MemberSession Member member
    ) {
        Post post = postService.getPostDetails(id);
        List<CommentResponse> comments = commentService.searchByPostId(post.getId(), member).stream()
                .map(CommentResponse::entityToResponse)
                .toList();
        return PostDetailResponse.toResponse(post, comments);
    }

    @GetMapping("/page/new")
    public Pagination<PostMainPageResponse> getLatestPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @MemberSession Member member
    ) {
        return postService.getLatestPosts(member, page, size);
    }

    @GetMapping("/page/grill")
    public Pagination<PostMainPageResponse> getGrillPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @MemberSession Member member
    ) {
        return postService.getGrillPosts(member, page, size);
    }

    @GetMapping("/page/hot")
    public Pagination<PostMainPageResponse> getHotPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @MemberSession Member member
    ) {
        return postService.getHotPosts(page, size, member);
    }
}

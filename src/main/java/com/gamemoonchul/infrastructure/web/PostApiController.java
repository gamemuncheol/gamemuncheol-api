package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.PostBanService;
import com.gamemoonchul.application.PostDeleteService;
import com.gamemoonchul.application.PostOpenApiService;
import com.gamemoonchul.application.PostService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;

import com.gamemoonchul.domain.entity.PostBan;

import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.response.PostDetailResponse;
import com.gamemoonchul.infrastructure.web.dto.request.PostUploadRequest;
import com.gamemoonchul.infrastructure.web.dto.response.PostMainPageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerWithEnvelopPattern
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;
    private final PostDeleteService postDeleteService;
    private final PostBanService postBanService;

    @PostMapping
    public PostDetailResponse upload(
            @Valid
            @RequestBody PostUploadRequest request,
            @MemberSession Member member
    ) {
        PostDetailResponse response = postService.upload(request, member);
        return response;
    }

    @DeleteMapping("/{id}")
    public String delete(
            @MemberSession Member member, @PathVariable("id") Long id
    ) {
        return postDeleteService.delete(id, member);
    }

    @GetMapping("/ban")
    public List<PostMainPageResponse> getBannedPosts(
            @MemberSession Member member
    ) {
        return postBanService.bannedPost(member.getId()).stream()
                .map(PostBan::getBanPost)
                .map(PostMainPageResponse::entityToResponse)
                .toList();
    }

    @PostMapping("/ban/{postId}")
    public void ban(
            @MemberSession Member member,
            @PathVariable(name = "postId") Long postId
    ) {
        Post post = postService.findById(postId);
        postBanService.ban(member, post);
    }
}

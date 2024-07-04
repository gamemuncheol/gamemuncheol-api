package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.PostDeleteService;
import com.gamemoonchul.application.PostService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.response.PostDetailResponse;
import com.gamemoonchul.infrastructure.web.dto.request.PostUploadRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestControllerWithEnvelopPattern
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;
    private final PostDeleteService postDeleteService;

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
}

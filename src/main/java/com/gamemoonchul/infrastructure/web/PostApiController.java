package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.PostService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.PostResponseDto;
import com.gamemoonchul.infrastructure.web.dto.PostUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestControllerWithEnvelopPattern
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @PostMapping("/upload")
    public PostResponseDto upload(
            @RequestBody PostUploadRequest request,
            @MemberSession Member member
    ) {
        PostResponseDto response = postService.upload(request, member);
        return response;
    }

    @DeleteMapping("/delete")
    public String delete(
            @MemberSession Member member, @RequestParam("id") Long id
    ) {
        return postService.delete(id, member);
    }

}

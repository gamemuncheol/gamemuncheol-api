package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.PostService;
import com.gamemoonchul.application.S3Service;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.PostResponseDto;
import com.gamemoonchul.infrastructure.web.dto.PostUploadRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestControllerWithEnvelopPattern
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @PostMapping
    public PostResponseDto upload(
            @Valid
            @RequestBody PostUploadRequest request,
            @MemberSession Member member
    ) {
        PostResponseDto response = postService.upload(request, member);
        return response;
    }

    @DeleteMapping("/{id}")
    public String delete(
            @MemberSession Member member, @PathVariable("id") Long id
    ) {
        return postService.delete(id, member);
    }
}

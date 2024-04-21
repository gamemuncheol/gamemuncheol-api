package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.PostService;
import com.gamemoonchul.application.S3Service;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.PostResponseDto;
import com.gamemoonchul.infrastructure.web.dto.PostUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestControllerWithEnvelopPattern
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;
    private final S3Service s3Service;

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

    @PostMapping("/video-upload")
    public String uploadVideo(@RequestParam("file") MultipartFile file) {
        String fileUrl = s3Service.uploadVideo(file);
        return fileUrl;
    }

    @PostMapping("/thumbnail-upload")
    public String uploadThumbnail(@RequestParam("file") MultipartFile file) {
        String fileUrl = s3Service.uploadImage(file);
        return fileUrl;
    }
}

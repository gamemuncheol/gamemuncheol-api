package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.S3Service;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/files")
@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
public class FileUploadController {
    private final S3Service s3Service;

    @PostMapping("/video")
    public String uploadVideo(@RequestParam("file") MultipartFile file) {
        String fileUrl = s3Service.uploadVideo(file);
        return fileUrl;
    }

    @PostMapping("/thumbnail")
    public String uploadThumbnail(@RequestParam("file") MultipartFile file) {
        String fileUrl = s3Service.uploadImage(file);
        return fileUrl;
    }
}

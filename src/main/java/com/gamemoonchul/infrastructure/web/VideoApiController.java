package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.VideoService;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestControllerWithEnvelopPattern
@RequestMapping("/open-api/video")
@RequiredArgsConstructor
public class VideoApiController {
    private final VideoService videoService;

    @PostMapping("/upload")
    public String saveFile(@RequestParam("file") MultipartFile file) {
        String fileName = videoService.upload(file);
        return fileName;
    }
}

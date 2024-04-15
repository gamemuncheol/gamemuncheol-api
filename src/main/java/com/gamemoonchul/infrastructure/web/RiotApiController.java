package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.RiotApiService;
import com.gamemoonchul.application.S3Service;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.MatchGameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/api/riot")
public class RiotApiController {
    private final RiotApiService riotApiService;
    private final S3Service s3Service;

    @GetMapping("/search-match/{gameId}")
    public MatchGameResponse searchMatch(@PathVariable String gameId) {
        return riotApiService.searchMatch("KR_" + gameId);
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

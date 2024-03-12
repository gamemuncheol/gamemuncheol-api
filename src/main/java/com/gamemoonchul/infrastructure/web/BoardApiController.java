package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.BoardService;
import com.gamemoonchul.application.S3Service;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.MatchGameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/api/board")
public class BoardApiController {
    private final BoardService boardService;
    private final S3Service videoService;

    @GetMapping("/searchMatch/{gameId}")
    public MatchGameResponse searchMatch(@PathVariable String gameId) {
        return boardService.searchMatch(gameId);
    }

    @PostMapping("/video-upload")
    public String uploadVideo(@RequestParam("file") MultipartFile file) {
        String fileName = videoService.upload(file);
        return fileName;
    }
}
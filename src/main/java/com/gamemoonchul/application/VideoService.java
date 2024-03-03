package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.status.VideoStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService {
    private static final long MAX_FILE_SIZE = 1024L * 1024L * 1024L * 10L; // 10GB

    private final YoutubeService youtubeService;

    public String upload(MultipartFile file) {

        checkFileTypeOrThrow(file);
        checkFileSizeOrThrow(file);

        String fileName = UUID.randomUUID() + ".mp4";
        String directoryPath = getDirectoryPath();
        Path filePath = Paths.get(directoryPath, fileName);
        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
            String youtubeUrl = youtubeService.upload(filePath);
            deleteFile(filePath);
            return youtubeUrl;
        } catch (IOException e) {
            log.error("file upload failed", e);
            throw new ApiException(VideoStatus.FILE_UPLOAD_FAILED);
        }
    }

    private void deleteFile(Path path) {
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                log.error("file delete failed", e);
            }
        }
    }

    /**
     * 파일을 저장할 디렉토리 경로를 가져오고 없다면 생성한다.
     */
    private String getDirectoryPath() {
        String dierctory = "src/main/resources/static/video";
        Path directtoryPath = Paths.get(dierctory);
        if (!Files.exists(directtoryPath)) {
            try {
                Files.createDirectories(directtoryPath);
            } catch (IOException e) {
                log.error("file upload failed", e);
                throw new ApiException(VideoStatus.FILE_UPLOAD_FAILED);
            }
        }
        return directtoryPath.toString();
    }


    /**
     * 파일 타입이 MP4인지 확인
     */
    private void checkFileTypeOrThrow(MultipartFile file) {
        String contentType = file.getContentType();
        if (!contentType.equals("video/mp4")) {
            throw new ApiException(VideoStatus.INVALID_FILETYPE);
        }
    }


    /**
     * 파일 사이즈가 10GB 이상인지 확인
     */
    private void checkFileSizeOrThrow(MultipartFile file) {
        long fileSize = file.getSize();
        if (fileSize >= MAX_FILE_SIZE) {
            throw new ApiException(VideoStatus.FILE_SIZE_EXCEEDED);
        }
    }
}

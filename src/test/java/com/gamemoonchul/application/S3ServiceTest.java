package com.gamemoonchul.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class S3ServiceTest {
    @Autowired
    private S3Service s3Service;
    @Test
    @DisplayName("S3에 파일 업로드 테스트")
    void upload() {
        // given
        String fileName = "test.mp4";
        MultipartFile file;
        try {
            byte[] content = Files.readAllBytes(Paths.get("src/test/resources/test.mp4"));
            file = new MockMultipartFile(fileName, fileName, "video/mp4", content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // when
        String result = s3Service.upload(file);

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("S3에 파일 존재하는지 확인")
    void isValidFile() {
        // given
        String fileName = "test.mp4";

        // when
        boolean result = s3Service.isValidFile(fileName);

        //then
        assertFalse(result);
    }
}
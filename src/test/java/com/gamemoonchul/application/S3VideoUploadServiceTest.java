package com.gamemoonchul.application;

import com.gamemoonchul.TestDataBase;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class S3VideoUploadServiceTest extends TestDataBase {
    private S3Service s3Service = super.s3Service();

    private String fileName = "test.mp4";

    @Test
    @Order(1)
    @DisplayName("S3에 파일 업로드 테스트")
    void upload() {
        // given
        MultipartFile file;
        try {
            byte[] content = Files.readAllBytes(Paths.get("src/test/resources/" + fileName));
            file = new MockMultipartFile(fileName, fileName, "video/mp4", content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // when
        fileName = s3Service.uploadVideo(file);


        //then
        assertNotNull(fileName);
    }

    @Test
    @Order(2)
    @DisplayName("S3에 파일 삭제 테스트")
    void delete() {
        // given

        // when
        s3Service.delete(fileName);

        //then
        assertFalse(s3Service.isValidFile(fileName));
    }

    @Test
    @Order(3)
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
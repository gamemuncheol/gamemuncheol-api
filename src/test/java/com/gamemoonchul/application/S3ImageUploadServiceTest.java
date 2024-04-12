package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
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

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class S3ImageUploadServiceTest {
    @Autowired
    private S3Service s3Service;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String fileName = "iu.png";

    @Test
    @Order(0)
    @DisplayName("S3에 잘못된 양식업로드 테스트")
    void uploadWrongType() throws IOException {
        // given
        MultipartFile file;
            byte[] content = Files.readAllBytes(Paths.get("src/test/resources/test.mp4"));
            file = new MockMultipartFile(fileName, fileName, "video/mp4", content);

        // when // then
        assertThrows(ApiException.class, () -> {
            s3Service.uploadImage(file);
        });
    }

    @Test
    @Order(1)
    @DisplayName("S3에 파일 업로드 테스트")
    void upload() {
        // given
        MultipartFile file;
        try {
            byte[] content = Files.readAllBytes(Paths.get("src/test/resources/" + fileName));
            file = new MockMultipartFile(fileName, fileName, "image/png", content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // when
        fileName = s3Service.uploadImage(file);

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
        // given // when
        boolean result = s3Service.isValidFile(fileName);

        //then
        assertFalse(result);
    }
}
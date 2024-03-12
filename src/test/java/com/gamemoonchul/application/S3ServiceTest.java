package com.gamemoonchul.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class S3ServiceTest {
    @Autowired
    private S3Service s3Service;

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
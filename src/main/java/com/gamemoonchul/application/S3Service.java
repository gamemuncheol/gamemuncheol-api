package com.gamemoonchul.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.status.VideoStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {
    public static BigInteger MAX_FILE_SIZE = BigInteger.valueOf(1024L).multiply(BigInteger.valueOf(1024L)).multiply(BigInteger.valueOf(500L)); // 500MB


    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file) {
//        checkFileSizeOrThrow(file);
        checkFileTypeOrThrow(file);

        try {
            String fileName = file.getOriginalFilename();
            String fileUrl = "https://" + bucket + "/test" + fileName;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            return fileUrl;
        } catch (IOException e) {
            log.error("S3 업로드에 실패하였습니다.", e);
            throw new ApiException(VideoStatus.S3_UPLOAD_FAILED);
        }
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
     * 파일 사이즈가 500MB 이상인지 확인
     */
//    private void checkFileSizeOrThrow(MultipartFile file) {
//        byte[] bytes;
//        try {
//            bytes = file.getBytes();
//        } catch (IOException e) {
//            throw new ApiException(VideoStatus.UNKOWN_ERROR);
//        }
//
//        BigInteger fileSize = BigInteger.valueOf(bytes.length);
//        if (fileSize >= MAX_FILE_SIZE) {
//            throw new ApiException(VideoStatus.FILE_SIZE_EXCEEDED);
//        }
//    }

}

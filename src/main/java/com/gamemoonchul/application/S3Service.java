package com.gamemoonchul.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
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


    public void delete(String fileName) {
        amazonS3Client.deleteObject(bucket, fileName);
    }

    /**
     * 파일이 유효한지 확인
     * @param fileName
     * @return 유효한 파일이면 true, 아니면 false
     */
    public boolean isValidFile(
            String fileName) {
        boolean isValidFile = true;
        try {
            ObjectMetadata objectMetadata = amazonS3Client.getObjectMetadata(bucket, fileName);
        } catch (AmazonS3Exception s3e) {
            if (s3e.getStatusCode() == 404) {
                // i.e. 404: NoSuchKey - The specified key does not exist
                isValidFile = false;
            } else {
                throw s3e;    // rethrow all S3 exceptions other than 404
            }
        }

        return isValidFile;
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
}

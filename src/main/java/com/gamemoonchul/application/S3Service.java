package com.gamemoonchul.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.common.exception.InternalServerException;
import com.gamemoonchul.domain.status.S3Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class S3Service {
    public static BigInteger MAX_FILE_SIZE = BigInteger
            .valueOf(1024L)
            .multiply(BigInteger.valueOf(1024L))
            .multiply(BigInteger.valueOf(500L)); // 500MB


    private final AmazonS3Client amazonS3Client;
    private final String bucket;

    @Autowired
    public S3Service(@Value("${cloud.aws.s3.bucket}") String bucket, AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
        this.bucket = bucket;
    }

    public String uploadVideo(MultipartFile file) {
        checkFileTypeOrThrow(file.getContentType(), FileType.VIDEO);
        try {
            String fileName = file.getOriginalFilename();
            String fileUrl = "https://gamemuncheol-s3.s3.ap-southeast-2.amazonaws.com/" + fileName;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            return fileUrl;
        } catch (IOException e) {
            log.error("S3 업로드에 실패하였습니다.", e);
            throw new InternalServerException(S3Status.S3_UPLOAD_FAILED);
        }
    }

    public String uploadImage(MultipartFile file) {
        checkFileTypeOrThrow(file.getContentType(), FileType.IMAGE);
        try {
            String fileName = file.getOriginalFilename();
            String fileUrl = "https://gamemuncheol-s3.s3.ap-southeast-2.amazonaws.com/" + fileName;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            return fileUrl;
        } catch (IOException e) {
            log.error("S3 업로드에 실패하였습니다.", e);
            throw new InternalServerException(S3Status.S3_UPLOAD_FAILED);
        }
    }


    public void delete(String fileName) {
        amazonS3Client.deleteObject(bucket, fileName);
    }

    /**
     * 파일이 유효한지 확인
     *
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
     * 동영상일 경우 : 파일 타입이 MP4인지 확인
     * 이미지일 경우 : 파일 타입이 jpeg, gif, png인지 확인
     */
    private void checkFileTypeOrThrow(String contentType, FileType type) {
        if (type == FileType.VIDEO) {
            if (!contentType.equals("video/mp4")) {
                log.error(S3Status.INVALID_FILETYPE.getMessage());
                throw new BadRequestException(S3Status.INVALID_FILETYPE);
            }
        } else {
            boolean fileTypeIsNotImage = !(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif"));
            if (fileTypeIsNotImage) {
                log.error(S3Status.INVALID_FILETYPE.getMessage());
                throw new BadRequestException(S3Status.INVALID_FILETYPE);
            }
        }

    }

    public enum FileType {
        IMAGE, VIDEO
    }
}

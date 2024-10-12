package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.InternalServerException;
import com.gamemoonchul.config.youtube.YoutubeAuth;
import com.gamemoonchul.domain.status.YoutubeStatus;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.common.collect.Lists;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
@Transactional
public class YoutubeService {
    private static final String VIDEO_FILE_FORMAT = "video/*";
    private static YouTube youtube;

    public String upload(Path filePath) {
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");

        try {
            Credential credential = YoutubeAuth.authorize(scopes, "uploadvideo");

            youtube = new YouTube.Builder(YoutubeAuth.HTTP_TRANSPORT, YoutubeAuth.JSON_FACTORY, credential).setApplicationName(
                "gamemuncheol").build();

            log.info("Uploading: {}", filePath);

            Video videoObjectDefiningMetadata = new Video();

            VideoStatus status = new VideoStatus();
            status.setPrivacyStatus("public");
            videoObjectDefiningMetadata.setStatus(status);

            VideoSnippet snippet = new VideoSnippet();

            Calendar cal = Calendar.getInstance();
            snippet.setTitle("Test Upload via Java on " + cal.getTime());
            snippet.setDescription(
                "Video uploaded via YouTube Data API V3 using the Java library " + "on " + cal.getTime());

            List<String> tags = new ArrayList<String>();
            tags.add("test");
            tags.add("example");
            tags.add("java");
            tags.add("YouTube Data API V3");
            tags.add("erase me");
            snippet.setTags(tags);

            videoObjectDefiningMetadata.setSnippet(snippet);

            InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT,
                Files.newInputStream(filePath));

            YouTube.Videos.Insert videoInsert = youtube.videos()
                .insert("snippet,statistics,status", videoObjectDefiningMetadata, mediaContent);

            MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

            uploader.setDirectUploadEnabled(false);

            MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    switch (uploader.getUploadState()) {
                        case INITIATION_STARTED:
                            System.out.println("Initiation Started");
                            break;
                        case INITIATION_COMPLETE:
                            System.out.println("Initiation Completed");
                            break;
                        case MEDIA_IN_PROGRESS:
                            System.out.println("Upload in progress");
                            try {
                                System.out.println("Upload percentage: " + uploader.getProgress());
                            } catch (NullPointerException e) {
                                log.error("Progress Listener is null", e);
                            }
                            break;
                        case MEDIA_COMPLETE:
                            System.out.println("Upload Completed!");
                            break;
                        case NOT_STARTED:
                            System.out.println("Upload Not Started!");
                            break;
                    }
                }
            };
            uploader.setProgressListener(progressListener);

            Video returnedVideo = videoInsert.execute();

            System.out.println("\n================== Returned Video ==================\n");
            log.info("\n================== Returned Video ==================\n");
            log.info("  - Id: {}", returnedVideo.getId());
            log.info("  - Title: {}", returnedVideo.getSnippet().getTitle());
            log.info("  - Tags: {}", returnedVideo.getSnippet().getTags());
            log.info("  - Privacy Status: {}", returnedVideo.getStatus().getPrivacyStatus());
            log.info("  - Video Count: {}", returnedVideo.getStatistics().getViewCount());
            return "https://www.youtube.com/watch?v=" + returnedVideo.getId();
        } catch (GoogleJsonResponseException e) {
            log.error("GoogleJsonResponseException: ", e);
            throw new InternalServerException(YoutubeStatus.JSON_PARSE_ERROR);
        } catch (IOException e) {
            throw new InternalServerException(YoutubeStatus.UNKNOWN_ERROR);
        } catch (Throwable t) {
            log.error("Throwable: ", t);
            throw new InternalServerException(YoutubeStatus.UNKNOWN_ERROR);
        }
    }
}

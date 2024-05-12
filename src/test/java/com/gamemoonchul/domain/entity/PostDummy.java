package com.gamemoonchul.domain.entity;

import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.infrastructure.web.dto.PostUploadRequest;

import java.util.ArrayList;
import java.util.List;


public class PostDummy {
    public static PostUploadRequest createRequest() {
        return PostUploadRequest.builder()
                .title("string")
                .content("string")
                .videoUrl("https://youtube.com/")
                .thumbnailUrl("https://s3/" )
                .matchUserIds(new ArrayList<>())
                .build();
    }

    public static PostUploadRequest createRequest(List<Long> matchUserIds) {
        return PostUploadRequest.builder()
                .title("string")
                .content("string")
                .videoUrl("https://youtube.com/")
                .thumbnailUrl("https://s3/" )
                .matchUserIds(matchUserIds)
                .build();
    }


    public static Post createPost(String value) {
        Post post = Post.builder()
                .title(value)
                .content(value)
                .videoUrl("https://youtube.com")
                .thumbnailUrl("https://s3.amazon.com")
                .build();
        return post;
    }
}
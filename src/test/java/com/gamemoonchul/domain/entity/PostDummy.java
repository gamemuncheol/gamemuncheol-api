package com.gamemoonchul.domain.entity;

import com.gamemoonchul.infrastructure.web.dto.PostUploadRequest;


public class PostDummy {
    public static PostUploadRequest createRequest() {
        return PostUploadRequest.builder()
                .title("string")
                .content("string")
                .videoUrl("https://youtube.com/")
                .thumbnailUrl("https://s3/" )
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
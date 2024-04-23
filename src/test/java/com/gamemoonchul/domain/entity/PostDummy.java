package com.gamemoonchul.domain.entity;

import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.enums.MemberRole;
import com.gamemoonchul.infrastructure.web.dto.PostUploadRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostDummy {
    public static PostUploadRequest createRequest() {
        return new PostUploadRequest(
                "youtube", "s3upload data", UUID.randomUUID()
                .toString(), UUID.randomUUID()
                .toString()
        );
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
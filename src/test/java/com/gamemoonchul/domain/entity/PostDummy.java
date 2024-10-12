package com.gamemoonchul.domain.entity;

import com.gamemoonchul.infrastructure.web.dto.request.PostUploadRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PostDummy {
    public static PostUploadRequest createRequest() {
        return PostUploadRequest.builder()
            .title("string")
            .content("string")
            .videoUrl("https://youtube.com/")
            .thumbnailUrl("https://s3/")
            .matchUserIds(new ArrayList<>())
            .build();
    }

    public static PostUploadRequest createRequest(List<Long> matchUserIds) {
        return PostUploadRequest.builder()
            .title("string")
            .content("string")
            .videoUrl("https://youtube.com/")
            .thumbnailUrl("https://s3/")
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

    public static Post createPost(String value, Member member) {
        Post post = Post.builder()
            .title(value)
            .content(value)
            .videoUrl("https://youtube.com")
            .thumbnailUrl("https://s3.amazon.com")
            .build();
        return post;
    }


    public static Post createPostWithSpecificMember(Member member) {
        Post post = Post.builder()
            .title("제목")
            .member(member)
            .content("내용")
            .videoUrl("https://youtube.com")
            .thumbnailUrl("https://s3.amazon.com")
            .voteOptions(VoteOptionsDummy.createVoteOptionsEmptyVote())
            .createdAt(LocalDateTime.now())
            .build();
        return post;
    }
}

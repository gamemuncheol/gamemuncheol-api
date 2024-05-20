package com.gamemoonchul.domain.entity;

import com.gamemoonchul.infrastructure.web.dto.PostUploadRequest;

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

    public static Post createHotPost(int firstCount, int secondCount) {
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .member(MemberDummy.create())
                .videoUrl("https://youtube.com")
                .thumbnailUrl("https://s3.amazon.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .voteOptions(VoteOptionsDummy.createHotVoteOptions(firstCount, secondCount))
                .build();
        return post;
    }

    public static Post createEmptyVotePost() {
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .videoUrl("https://youtube.com")
                .thumbnailUrl("https://s3.amazon.com")
                .voteOptions(VoteOptionsDummy.createVoteOptionsEmptyVote())
                .createdAt(LocalDateTime.now())
                .build();
        return post;
    }

    public static Post createPostWithMember() {
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .member(MemberDummy.create())
                .videoUrl("https://youtube.com")
                .thumbnailUrl("https://s3.amazon.com")
                .voteOptions(VoteOptionsDummy.createVoteOptionsEmptyVote())
                .createdAt(LocalDateTime.now())
                .build();
        return post;
    }
}
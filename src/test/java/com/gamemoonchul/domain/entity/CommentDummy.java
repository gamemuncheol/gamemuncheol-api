package com.gamemoonchul.domain.entity;

import com.gamemoonchul.infrastructure.web.dto.CommentRequest;

public class CommentDummy {
    public static CommentRequest createRequest(Long postId) {
        return new CommentRequest("content", postId);
    }

    public static Comment create(Post post, Member member) {
        return Comment.builder()
                .member(member)
                .post(post)
                .content("DKJFkldfhdskalhgajklfg ukesagfhkjlasgjurelagrfhjagfhjksd fkjsfg jyf")
                .build();
    }
}

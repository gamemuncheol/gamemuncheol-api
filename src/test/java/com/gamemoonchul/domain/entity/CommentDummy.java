package com.gamemoonchul.domain.entity;

import com.gamemoonchul.infrastructure.web.dto.request.CommentSaveRequest;

public class CommentDummy {
    public static CommentSaveRequest createSaveDto(Long postId) {
        return new CommentSaveRequest(null, "content", postId);
    }

    public static CommentSaveRequest createSaveDto(Long parentId, Long postId) {
        return new CommentSaveRequest(parentId, "content", postId);
    }

    public static Comment create(Post post, Member member) {
        return Comment.builder()
            .member(member)
            .post(post)
            .content("DKJFkldfhdskalhgajklfg ukesagfhkjlasgjurelagrfhjagfhjksd fkjsfg jyf")
            .build();
    }
}

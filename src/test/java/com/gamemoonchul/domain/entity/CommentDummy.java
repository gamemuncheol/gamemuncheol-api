package com.gamemoonchul.domain.entity;

import com.gamemoonchul.domain.model.dto.CommentSaveDto;
import com.gamemoonchul.infrastructure.web.dto.request.CommentRequest;

public class CommentDummy {
    public static CommentRequest createRequest(Long postId) {
        return new CommentRequest("content", postId);
    }

    public static CommentSaveDto createSaveDto(Long postId) {
        return new CommentSaveDto(null, "content", postId);
    }

    public static CommentSaveDto createSaveDto(Long parentId, Long postId) {
        return new CommentSaveDto(parentId, "content", postId);
    }

    public static Comment create(Post post, Member member) {
        return Comment.builder()
                .member(member)
                .post(post)
                .content("DKJFkldfhdskalhgajklfg ukesagfhkjlasgjurelagrfhjagfhjksd fkjsfg jyf")
                .build();
    }
}

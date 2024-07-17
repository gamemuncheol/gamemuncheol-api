package com.gamemoonchul.application.converter;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.web.dto.request.PostUploadRequest;

public class PostConverter {
    public static Post requestToEntity(PostUploadRequest request, Member member) {
        Post entity = Post.builder()
                .title(request.title())
                .videoUrl(request.videoUrl())
                .thumbnailUrl(request.thumbnailUrl())
                .content(request.content())
                .tags(request.tags())
                .build();
        entity.setMember(member);
        return entity;
    }
}

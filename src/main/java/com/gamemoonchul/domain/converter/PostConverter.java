package com.gamemoonchul.domain.converter;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.web.dto.PostResponseDto;
import com.gamemoonchul.infrastructure.web.dto.PostUploadRequest;
import org.springframework.stereotype.Service;

@Service
public class PostConverter {

    public static PostResponseDto toResponse(Post entity) {
        return PostResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle()).member(MemberConverter.toResponseDto(entity.getMember()))
                .thumbnailUrl(entity.getThumbnailUrl())
                .videoUrl(entity.getVideoUrl())
                .viewCount(entity.getViewCount())
                .build();
    }

    public static Post requestToEntity(
            PostUploadRequest request, Member member
    ) {
        Post entity = Post.builder().title(
                request.title()
        ).videoUrl(request.videoUrl()).thumbnailUrl(request.thumbnailUrl()).build();
        entity.setMember(member);
        return entity;
    }
}

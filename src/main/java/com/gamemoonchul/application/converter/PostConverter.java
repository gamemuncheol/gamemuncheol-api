package com.gamemoonchul.application.converter;

import com.gamemoonchul.application.member.MemberConverter;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.web.dto.response.PostMainPageResponse;
import com.gamemoonchul.infrastructure.web.dto.response.PostResponseDto;
import com.gamemoonchul.infrastructure.web.dto.request.PostUploadRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostConverter {

    public static PostMainPageResponse toMainResponse(Post entity) {
        return PostMainPageResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .member(MemberConverter.toResponseDto(entity.getMember()))
                .thumbnailUrl(entity.getThumbnailUrl())
                .videoUrl(entity.getVideoUrl())
                .viewCount(entity.getViewCount())
                .voteRatio(List.of(100 - entity.getVoteRatio(), entity.getVoteRatio()))
                .build();
    }

    public static PostResponseDto toResponse(Post entity) {
        return PostResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .member(MemberConverter.toResponseDto(entity.getMember()))
                .thumbnailUrl(entity.getThumbnailUrl())
                .videoUrl(entity.getVideoUrl())
                .viewCount(entity.getViewCount())
                .build();
    }

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

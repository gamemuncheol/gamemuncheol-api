package com.gamemoonchul.application.converter;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.dto.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentConverter {
    private final PostRepository postRepository;

    public Comment requestToEntity(Member member, CommentRequest request) {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(
                        () ->
                                new ApiException(PostStatus.POST_NOT_FOUND)
                );

        return Comment.builder()
                .member(member)
                .post(post)
                .content(request.content())
                .build();
    }
}

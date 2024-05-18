package com.gamemoonchul.application.converter;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.CommentRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.dto.CommentFixRequest;
import com.gamemoonchul.infrastructure.web.dto.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.gamemoonchul.domain.entity.QPost.post;

@Service
@RequiredArgsConstructor
public class CommentConverter {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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

    public Comment requestToEntity(CommentFixRequest request) {
        Comment savedComment = commentRepository.findById(request.commentId())
                .orElseThrow(() ->
                        new ApiException(PostStatus.COMMENT_NOT_FOUND)
                );
        savedComment.setContent(request.contents());
        return savedComment;
    }
}

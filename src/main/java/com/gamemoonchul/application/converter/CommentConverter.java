package com.gamemoonchul.application.converter;

import com.gamemoonchul.common.exception.NotFoundException;
import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.model.dto.CommentSaveDto;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.CommentRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.dto.request.CommentFixRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentConverter {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Comment requestToEntity(Member member, CommentSaveDto request) {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(
                        () -> {
                            log.error(PostStatus.POST_NOT_FOUND.getMessage());
                            return new NotFoundException(PostStatus.POST_NOT_FOUND);
                        }
                );

        return Comment.builder()
                .parentId(request.parentId())
                .member(member)
                .post(post)
                .content(request.content())
                .build();
    }

    /**
     * request의 comment id를 참조해서 실제로 영속 상태인 Comment Entity를 반환
     */
    public Comment requestToEntity(CommentFixRequest request) {
        Comment savedComment = commentRepository.searchByIdOrThrow(request.commentId());
        savedComment.setContent(request.contents());
        return savedComment;
    }
}

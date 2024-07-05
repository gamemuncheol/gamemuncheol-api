package com.gamemoonchul.application;

import com.gamemoonchul.application.converter.CommentConverter;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.common.exception.NotFoundException;
import com.gamemoonchul.common.exception.UnauthorizedException;
import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.model.dto.CommentSaveDto;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.CommentRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.dto.request.CommentFixRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentConverter commentConverter;

    public List<Comment> searchByPostId(Long postId, Member member) {
        return commentRepository.searchByPostId(postId, member);
    }

    /**
     * 테스트를 하기 위해서 실제 Post, Member가 객체맵핑 되있어야 합니다.
     */
//    @Retryable(
//            retryFor = {
//                    ObjectOptimisticLockingFailureException.class,
//                    StaleObjectStateException.class,
//                    ObjectOptimisticLockingFailureException.class},
//            maxAttempts = 1000, // 최대 시도 횟수
//            backoff = @Backoff(1000) // 재시도 간의 대기 시간 (1000ms)
//    )
    @Transactional
    public Comment save(CommentSaveDto request, Member member) {
        validatePostNotReplied(request);
        Post post = postRepository.findByIdForUpdate(request.postId())
                .orElseThrow(() -> new NotFoundException(PostStatus.POST_NOT_FOUND));
        Comment comment = commentConverter.requestToEntity(member, request);

        log.info("[CUSTOM LOG] PESSIMISTIC_WRITE 락 획득: commentCount = {}", post.getCommentCount());

        long count = post.getCommentCount();
        count++;
        post.setCommentCount(count);
        postRepository.save(post);
        log.info("[CUSTOM LOG] post 저장 : commentCount = {}", post.getCommentCount());

        return commentRepository.save(comment);
    }

    /**
     * 부모의 Comment가 또 부모를 가지지 않는지 검증
     */
    private void validatePostNotReplied(CommentSaveDto request) {
        if (request.parentId() != null) {
            Comment parentComment = commentRepository.findById(request.parentId())
                    .orElseThrow(() -> new BadRequestException(PostStatus.COMMENT_NOT_FOUND));
            if (parentComment.getParentId() != null) {
                throw new BadRequestException(PostStatus.COMMENT_CANT_HAVE_GRANDMOTHER);
            } else if (!parentComment.getPost()
                    .getId()
                    .equals(request.postId())) {
                throw new BadRequestException(PostStatus.INVALID_REPLY);
            }
        }
    }

    public Comment searchComment(Long commentId) {
        Comment result = commentRepository.searchByIdOrThrow(commentId);
        return result;
    }

    public Comment fix(CommentFixRequest request, Member authMember) {
        Comment fixedComment = commentConverter.requestToEntity(request);
        // 글 작성자
        validateSameMemberId(fixedComment.getMember(), authMember);
        return commentRepository.save(fixedComment);
    }

    public void delete(Long commentId, Member authMember) {
        Comment savedComment = this.searchComment(commentId);
        validateSameMemberId(savedComment.getMember(), authMember);

        if (!savedComment.parentExist()) { // 대댓글이 아닐경우 자기 자신의 대댓글들 삭제
            List<Comment> children = commentRepository.findByParentId(savedComment.getId());
            commentRepository.deleteAll(children);
        }
        commentCountDown(savedComment.getPost());
        commentRepository.delete(savedComment);
    }

    private void commentCountDown(Post post) {
        post.commentCountDown();
        postRepository.save(post);
    }

    private void validateSameMemberId(Member commentWriteMember, Member currentSignInMember) {
        if (commentWriteMember.getId()
                .equals(currentSignInMember.getId())) {
            return;
        }
        throw new UnauthorizedException(MemberStatus.NOT_AUTHORIZED_MEMBER);
    }

}

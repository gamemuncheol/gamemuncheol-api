package com.gamemoonchul.application;

import com.gamemoonchul.application.converter.CommentConverter;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.common.exception.NotFoundException;
import com.gamemoonchul.common.exception.UnauthorizedException;
import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.CommentRepository;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.dto.CommentFixRequest;
import com.gamemoonchul.infrastructure.web.dto.CommentRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentConverter commentConverter;
    private final MemberRepository memberRepository;

    /**
     * 테스트를 하기 위해서 실제 Post, Member가 객체맵핑 되있어야 합니다.
     */
    public Comment save(CommentRequest request, Member member) {
        Comment comment = commentConverter.requestToEntity(member, request);
        Post post = postRepository.findById(request.postId())
                .orElseThrow(
                        () -> new NotFoundException(PostStatus.POST_NOT_FOUND)
                );
        post.addComment(comment);
        postRepository.save(post);
        return commentRepository.save(comment);
    }

    public Comment searchComment(Long commentId)  {
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
        commentRepository.delete(savedComment);
    }

    private void validateSameMemberId(Member commentWriteMember, Member currentSignInMember) {
        if (commentWriteMember.getId().equals(currentSignInMember.getId())) {
            return;
        }
        throw new UnauthorizedException(MemberStatus.NOT_AUTHORIZED_MEMBER);
    }

}

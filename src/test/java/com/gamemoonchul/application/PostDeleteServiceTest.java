package com.gamemoonchul.application;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.application.post.PostDeleteService;
import com.gamemoonchul.application.post.PostService;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.common.exception.UnauthorizedException;
import com.gamemoonchul.domain.entity.*;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.CommentRepository;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.dto.request.PostUploadRequest;
import com.gamemoonchul.infrastructure.web.dto.response.PostDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PostDeleteServiceTest extends TestDataBase {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostDeleteService postDeleteService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("권한 없는 사용자가 삭제 요청했을 경우 에러 확인")
    public void unauthorizedDeleteTest() {
        // given
        Member member1 = MemberDummy.createWithId("1");
        Member member2 = MemberDummy.createWithId("2");
        memberRepository.saveAll(List.of(member1, member2));
        PostUploadRequest request = PostDummy.createRequest();

        // when
        PostDetailResponse response = postService.upload(PostDummy.createRequest(), member1);

        // then
        assertThatThrownBy(() -> postDeleteService.delete(response.getId(), member2.getId())).isInstanceOf(UnauthorizedException.class)
            .hasMessageContaining(PostStatus.UNAUTHORIZED_REQUEST.getMessage());
    }

    @Test
    @DisplayName("게시물 삭제시 댓글 삭제 확인")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void deletePostAndComment() {
        // given
        Member member = memberRepository.save(MemberDummy.create());
        PostDetailResponse response = postService.upload(PostDummy.createRequest(), member);
        Post savedPost = postRepository.findById(response.getId())
            .orElseThrow(() -> new BadRequestException(PostStatus.POST_NOT_FOUND));
        Comment comment = CommentDummy.create(savedPost, member);
        commentRepository.save(comment);

        // when
        postDeleteService.delete(response.getId(), member.getId());

        // then
        assertThat(commentRepository.findAllByPost(savedPost)
            .isEmpty()).isTrue();
    }
}

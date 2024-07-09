package com.gamemoonchul.application;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.domain.entity.*;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.CommentRepository;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BannedCommentTest extends TestDataBase {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberBanService memberBanService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("차단된 유저의 댓글 보이지 않는지 테스트")
    void blockedMemberCommentNotVisibleTest() {
        // given
        Member me = MemberDummy.createWithId("홍길동");
        Member banMember = MemberDummy.createWithId("적길동");
        Member anotherMember = MemberDummy.createWithId("청길동");
        memberRepository.saveAll(List.of(me, banMember, anotherMember));
        memberBanService.ban(me, banMember.getId());
        Post post = PostDummy.createPostWithSpecificMember(me);
        post = postRepository.save(post);
        Comment comment = CommentDummy.create(post, banMember);
        commentRepository.save(comment);

        // when
        List<Comment> bannedComments = commentService.searchByPostId(post.getId(), me);
        List<Comment> comments = commentService.searchByPostId(post.getId(), anotherMember);

        // then
        assertThat(bannedComments).isEmpty();
        assertThat(comments.size()).isEqualTo(1);
    }
}

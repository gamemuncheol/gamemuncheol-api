package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberDummy;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.PostDummy;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.dto.PostUploadRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시물 업로드 테스트 ")
    public void default_upload_test() {
        // given
        PostUploadRequest request = PostDummy.createRequest();
        Member member = memberRepository.save(MemberDummy.create());

        // when
        postService.upload(request, member);

        // then
        assertThat(postRepository.findAll().size()).isEqualTo(1);
    }
}
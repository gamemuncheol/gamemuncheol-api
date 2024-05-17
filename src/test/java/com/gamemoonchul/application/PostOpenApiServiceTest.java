package com.gamemoonchul.application;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberDummy;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.PostDummy;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.dto.PostResponseDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostOpenApiServiceTest extends TestDataBase {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostOpenApiService postOpenApiService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void set100Posts() {
        Member member = MemberDummy.createWithId("1");
        memberRepository.save(member);
        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Post dummy = PostDummy.createPost("value" + i);
            dummy.setMember(member);
            posts.add(dummy);
        }
        postRepository.saveAll(posts);
    }

    @Test
    @DisplayName("제일 처음 포스트는 반드시 다음에 나오는 포스트보다 이전 시간인지 확인, 투표 항목이 없기 때문에 0:0으로 나와야 함")
    void latestSorting() {
        // given
        Pageable pageable = PageRequest.of(0, 50);

        // when
        List<PostResponseDto> searchedPost = postOpenApiService.fetchByLatest(pageable)
                .getData();

        // then
        for (int i = 1; i < searchedPost.size(); i++) {
            assertTrue(searchedPost.get(i).getCreatedAt()
                    .isAfter(searchedPost.get(i - 1).getCreatedAt()));
            searchedPost.get(0).getVoteRates()
                    .forEach(voteRate -> {
                        assertThat(voteRate.getRate()).isEqualTo(0);
                    });
        }
    }
}
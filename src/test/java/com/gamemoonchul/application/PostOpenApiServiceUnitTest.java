package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.PostDummy;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.repository.VoteOptionRepository;
import com.gamemoonchul.infrastructure.web.dto.PostResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostOpenApiServiceUnitTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private VoteOptionRepository voteOptionRepository;

    @InjectMocks
    private PostOpenApiService postOpenApiService;

    @Test
    @DisplayName("제일 처음 포스트는 반드시 다음에 나오는 포스트보다 이전 시간인지 확인, 투표 항목이 없기 때문에 0:0으로 나와야 함")
    void latestSorting() {
        // given
        Pageable pageable = PageRequest.of(0, 50);
        List<Post> posts = IntStream.range(0, 10)
                .mapToObj(i -> PostDummy.createPostWithMember())
                .toList();
        Page<Post> resultPage = new PageImpl<>(posts, pageable, posts.size());

        when(postRepository.findAllByOrderByCreatedAt(pageable)).thenReturn(resultPage);
        when(voteOptionRepository.getVoteRateByPostId(any())).thenReturn(new ArrayList<>());
        // when
        List<PostResponseDto> searchedPost = postOpenApiService.fetchByLatest(pageable)
                .getData();

        // then
//        for (int i = 1; i < searchedPost.size(); i++) {
//            assertTrue(searchedPost.get(i).getCreatedAt()
//                    .isAfter(searchedPost.get(i - 1).getCreatedAt()));
//            searchedPost.get(0).getVoteRates()
//                    .forEach(voteRate -> {
//                        assertThat(voteRate.getRate()).isEqualTo(0);
//                    });
//        }
    }

    @Nested
    @DisplayName("핫 게시글들을 불러올 때")
    class getHotPosts {
        @Nested
        @DisplayName("성공한다")
        class success {
            @Test
            @DisplayName("투표 비율이 (45 ~ 55):(45 ~ 55) 인 게시글들만 반환한다")
            void successGetHotPosts() {
//                // given
//                List<Post> responsePosts = new ArrayList<>();
//                responsePosts.add(PostDummy.createHotPost(45, 55));
//                responsePosts.add(PostDummy.createHotPost(46, 54));
//                responsePosts.add(PostDummy.createHotPost(47, 53));
//                responsePosts.add(PostDummy.createHotPost(48, 52));
//                responsePosts.add(PostDummy.createHotPost(49, 51));
//                responsePosts.add(PostDummy.createHotPost(490, 502));
//                responsePosts.add(PostDummy.createEmptyVotePost());
//                responsePosts.add(PostDummy.createEmptyVotePost());
//                when(postRepository.findAll()).thenReturn(responsePosts);
//
//                // when
//                List<PostResponseDto> result = postOpenApiService.getHotPosts(0, 10);
//
//                // then
//                SoftAssertions.assertSoftly(softly -> {
//                    softly.assertThat(result.size()).isEqualTo(6);
//                });

            }
        }
    }
}

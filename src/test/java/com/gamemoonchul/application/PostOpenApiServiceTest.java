package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.PostDummy;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostOpenApiServiceTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostOpenApiService postOpenApiService;

    @BeforeEach
    void set100Posts() {
        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Post dummy = PostDummy.createPost("value" + i);
            posts.add(dummy);
        }
        postRepository.saveAll(posts);
    }

    @Test
    @DisplayName("제일 처음 포스트는 반드시 다음에 나오는 포스트보다 이전 시간인지 확인")
    void latestSorting() {
        // given
        Pageable pageable = PageRequest.of(0, 50);

        // when
        List<PostResponseDto> searchedPost = postOpenApiService.fetchByLatest(pageable)
                .getData();

        // then
        PostResponseDto lastPost = searchedPost.get(0);

        for (int i = 1; i < searchedPost.size(); i++) {
            assertTrue(searchedPost.get(i)
                    .getCreatedAt()
                    .isAfter(lastPost.getCreatedAt()));
            lastPost = searchedPost.get(i);
        }
    }
}
package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.PostView;
import com.gamemoonchul.infrastructure.repository.PostViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostViewService {
    private final PostViewRepository postViewRepository;

    // 새 트랜잭션 생성
    public void save(Post post) {
        PostView postView = PostView.builder()
            .post(post)
            .build();

        postViewRepository.save(postView);
    }
}

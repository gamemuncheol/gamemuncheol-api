package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.PostView;
import com.gamemoonchul.infrastructure.repository.PostViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostViewService {
    private final PostViewRepository postViewRepository;

    public List<PostView> findByCreatedAtAfterAndPostId(LocalDateTime createdAt, Long postId) {
        return postViewRepository.findByCreatedAtAfterAndPostId(createdAt, postId);
    }

    public void deleteAll(List<PostView> postViews) {
        postViewRepository.deleteAll(postViews);
    }
}

package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostOpenApiService {
    private final PostRepository postRepository;

    public Pagination<Post> fetchByLatest(Pageable pageable) {
        Page<Post> savedData = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return new Pagination<Post>(savedData);
    }
}

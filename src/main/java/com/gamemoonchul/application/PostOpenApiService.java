package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostOpenApiService {
    private final PostRepository postRepository;

    public Pagination<Post> fetchByLatest(Pageable pageable) {
        Page<Post> savedData = postRepository.findAllByOrderByCreatedAt(pageable);
        return new Pagination<Post>(savedData);
    }
}

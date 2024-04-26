package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import com.gamemoonchul.infrastructure.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostOpenApiService {
    private final PostRepository postRepository;

    public Pagination<PostResponseDto> fetchByLatest(Pageable pageable) {
        Page<Post> savedPage = postRepository.findAllByOrderByCreatedAt(pageable);
        List<PostResponseDto> responses = savedPage.getContent()
                .stream()
                .map(
                        PostResponseDto::entityToResponse
                )
                .toList();


        return new Pagination<PostResponseDto>(savedPage, responses);
    }
}

package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.repository.VoteOptionRepository;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import com.gamemoonchul.infrastructure.web.dto.PostMainPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostOpenApiService {
    private final PostRepository postRepository;
    private final VoteOptionRepository voteOptionRepository;

    public Pagination<PostMainPageResponse> fetchByLatest(Pageable pageable) {
        Page<Post> savedPage = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<PostMainPageResponse> responses = savedPage.getContent()
                .stream()
                .map(PostMainPageResponse::entityToResponse
                )
                .collect(Collectors.toList());
        return new Pagination<PostMainPageResponse>(savedPage, responses);
    }

    public Pagination<PostMainPageResponse> getGrillPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Double standardRatio = 45.0;
        Page<Post> savedPage = postRepository.findByVoteRatioGreaterThanEqualOrderByVoteCountDesc(standardRatio, pageable);
        List<PostMainPageResponse> responses = savedPage.getContent()
                .stream()
                .map(PostMainPageResponse::entityToResponse)
                .toList();
        return new Pagination<PostMainPageResponse>(savedPage, responses);
    }
}

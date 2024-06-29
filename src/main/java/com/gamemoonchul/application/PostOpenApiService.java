package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.repository.VoteOptionRepository;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import com.gamemoonchul.infrastructure.web.dto.PostResponseDto;
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

    public Pagination<PostResponseDto> getLatestPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> savedPage = postRepository.findAllByOrderByCreatedAt(pageable);
        List<PostResponseDto> responses = savedPage.getContent()
                .stream()
                .map(PostResponseDto::entityToResponse
                )
                .sorted(
                        Comparator.comparing(PostResponseDto::getCreatedAt)
                )
                .collect(Collectors.toList());

        return new Pagination<PostResponseDto>(savedPage, responses);
    }

    public Pagination<PostResponseDto> getGrillPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Double standardRatio = 45.0;
        Page<Post> savedPage = postRepository.findByVoteRatioGreaterThanEqual(standardRatio, pageable);
        List<PostResponseDto> responses = savedPage
                .getContent()
                .stream()
                .map(PostResponseDto::entityToResponse)
                .sorted(
                        Comparator.comparing(PostResponseDto::getCreatedAt)
                )
                .toList();
        return new Pagination<PostResponseDto>(savedPage, responses);
    }

    public Pagination<PostResponseDto> getHotPosts(int page, int size) {
        Pageable pageable = getPageable(page, size);
        Page<Post> savedPage = postRepository.findAllByOrderByViewCountDesc(pageable);
        List<PostResponseDto> responses = savedPage
                .getContent()
                .stream()
                .map(PostResponseDto::entityToResponse)
                .sorted(
                        Comparator.comparing(PostResponseDto::getCreatedAt)
                )
                .toList();
        return new Pagination<PostResponseDto>(savedPage, responses);
    }

    private Pageable getPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return pageable;
    }
}

package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.model.dto.VoteRate;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.repository.VoteOptionRepository;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import com.gamemoonchul.infrastructure.web.dto.PostMainResponse;
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

    public Pagination<PostResponseDto> fetchByLatest(Pageable pageable) {
        Page<Post> savedPage = postRepository.findAllByOrderByCreatedAt(pageable);
        List<PostResponseDto> responses = savedPage.getContent()
                .stream()
                .map(post -> {
                            List<VoteRate> voteRates = voteOptionRepository.getVoteRateByPostId(post.getId());
                            return PostResponseDto.entityToResponse(post, voteRates);
                        }
                )
                .sorted(
                        Comparator.comparing(PostResponseDto::getCreatedAt)
                )
                .collect(Collectors.toList());

        return new Pagination<PostResponseDto>(savedPage, responses);
    }

    public List<PostMainResponse> getHotPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Double standardRatio = 45.0;
        return postRepository.findByVoteRatioGreaterThanEqual(standardRatio, pageable)
                .getContent()
                .stream()
                .skip((long) page * size) // 건너뛸 요소 수 계산
                .limit(size) // 요소 수 제한
                .map(PostMainResponse::entityToResponse)
                .sorted(
                        Comparator.comparing(PostMainResponse::getCreatedAt)
                )
                .toList();
    }
}

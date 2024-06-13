package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.model.dto.VoteRate;
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

import java.util.*;
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
                            List<VoteRate> newVoteRates = calculateVoteRatio(post, voteRates);
                            return PostResponseDto.entityToResponse(post, newVoteRates);
                        }
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
                .map(post -> {
                    List<VoteRate> voteRates = voteOptionRepository.getVoteRateByPostId(post.getId());
                    List<VoteRate> newVoteRates = calculateVoteRatio(post, voteRates);
                    return PostResponseDto.entityToResponse(post, newVoteRates);
                })
                .sorted(
                        Comparator.comparing(PostResponseDto::getCreatedAt)
                )
                .toList();
        return new Pagination<PostResponseDto>(savedPage, responses);
    }

    private List<VoteRate> calculateVoteRatio(Post post, List<VoteRate> voteRates) {
        // key : voteOptionsId, value : 전체 수량
        HashMap<Long, Double> voteOptionsMap = new HashMap<>();
        HashMap<Long, Double> voteOptionsRatio = new HashMap<>();
        Double temp = 0.;
        if (post.isVotesNull()) {
            voteRates.forEach(voteRate -> voteRate.setRatio(0.));
            return voteRates;
        }
        for (Map.Entry<Long, Long> e : post.getVotes()
                .entrySet()) {
            Double cur = voteOptionsMap.getOrDefault(e.getValue(), 0.);
            cur++;
            temp++;
            voteOptionsMap.put(e.getValue(), cur);
        }
        final Double total = temp;
        voteOptionsMap.entrySet()
                .forEach(
                        e -> {
                            voteOptionsMap.values()
                                    .forEach(i -> {
                                        voteOptionsRatio.put(e.getKey(), i / total * 100.);
                                    });
                        }
                );
        voteRates.forEach(voteRate -> {
            Double ratio = voteOptionsRatio.get(voteRate.getVoteOptionsId());
            voteRate.setRatio(ratio);
        });
        return voteRates;
    }
}

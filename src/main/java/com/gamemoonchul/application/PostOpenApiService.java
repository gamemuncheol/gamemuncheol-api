package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import com.gamemoonchul.infrastructure.web.dto.response.PostMainPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostOpenApiService {
    private final PostRepository postRepository;
    private final PostBanService postBanService;
    private final MemberBanService memberBanService;

    public Pagination<PostMainPageResponse> getLatestPosts(Member member, int page, int size) {
        List<Long> banPostIds = getBannedPostIds(member);

        Page<Post> savedPage;
        if (banPostIds.isEmpty()) {
            savedPage = postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
        } else {
            savedPage = postRepository.findAllByIdNotInOrderByCreatedAtDesc(banPostIds, PageRequest.of(page, size));
        }
        List<PostMainPageResponse> responses = savedPage.getContent()
                .stream()
                .map(PostMainPageResponse::entityToResponse)
                .collect(Collectors.toList());

        return new Pagination<>(savedPage, responses);
    }

    public Pagination<PostMainPageResponse> getGrillPosts(Member member, int page, int size) {
        Double standardRatio = 45.0;
        List<Long> banPostIds = postBanService.getBanPostIds(member);

        Page<Post> savedPage;
        if (banPostIds.isEmpty()) {
            savedPage = postRepository.findAllByVoteRatioGreaterThanEqual(standardRatio, PageRequest.of(page, size));
        } else {
            savedPage = postRepository.findAllByIdNotInAndVoteRatioGreaterThanEqual(banPostIds, standardRatio, PageRequest.of(page, size));
        }
        List<PostMainPageResponse> responses = savedPage.getContent()
                .stream()
                .map(PostMainPageResponse::entityToResponse)
                .toList();
        return new Pagination<PostMainPageResponse>(savedPage, responses);
    }

    public Pagination<PostMainPageResponse> getHotPosts(int page, int size, Member member) {
        Page<Post> savedPage = postRepository.searchHotPostWithoutBanPosts(Optional.ofNullable(member)
                .map(Member::getId)
                .orElse(null), PageRequest.of(page, size));

        List<PostMainPageResponse> responses = savedPage.getContent()
                .stream()
                .map(PostMainPageResponse::entityToResponse)
                .toList();
        return new Pagination<>(savedPage, responses);
    }

    private List<Long> getBannedPostIds(Member member) {
        List<Long> banPostIds = new ArrayList<>();
        if (member != null) {
            postBanService.getBanPostIds(member);
            memberBanService.getBanPostIds(member)
                    .forEach(bannedId -> {
                        if (!banPostIds.contains(bannedId)) {
                            banPostIds.add(bannedId);
                        }
                    });

        }
        return banPostIds;
    }
}

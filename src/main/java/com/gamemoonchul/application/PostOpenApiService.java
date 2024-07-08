package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.CommentRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.common.Pagination;
import com.gamemoonchul.infrastructure.web.dto.response.CommentResponse;
import com.gamemoonchul.infrastructure.web.dto.response.PostDetailResponse;
import com.gamemoonchul.infrastructure.web.dto.response.PostMainPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostOpenApiService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostDetailResponse getPostDetails(Long postId, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException(PostStatus.POST_NOT_FOUND));
        PostDetailResponse response = PostDetailResponse.toResponse(post, getComments(postId, member));
        return response;
    }

    private List<CommentResponse> getComments(Long postId, Member member) {
        List<CommentResponse> response = commentRepository.searchByPostId(postId, member)
                .stream()
                .map(CommentResponse::entityToResponse)
                .toList();
        return response;
    }

    public Pagination<PostMainPageResponse> getLatestPosts(Member member, int page, int size) {
        Page<Post> savedPage = postRepository.searchNewPostsWithoutBanPosts(Optional.ofNullable(member)
                .map(Member::getId)
                .orElse(null), PageRequest.of(page, size));
        List<PostMainPageResponse> responses = savedPage.getContent()
                .stream()
                .map(PostMainPageResponse::entityToResponse)
                .collect(Collectors.toList());

        return new Pagination<>(savedPage, responses);
    }

    public Pagination<PostMainPageResponse> getGrillPosts(Member member, int page, int size) {
        Page<Post> savedPage = postRepository.searchGrillPostsWithoutBanPosts(Optional.ofNullable(member)
                .map(Member::getId)
                .orElse(null), PageRequest.of(page, size));
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
}

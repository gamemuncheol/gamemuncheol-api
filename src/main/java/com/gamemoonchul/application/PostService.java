package com.gamemoonchul.application;

import com.gamemoonchul.application.converter.PostConverter;
import com.gamemoonchul.common.exception.NotFoundException;
import com.gamemoonchul.common.exception.UnauthorizedException;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.repository.VoteOptionRepository;
import com.gamemoonchul.infrastructure.web.dto.PostMainPageResponse;
import com.gamemoonchul.infrastructure.web.dto.PostUploadRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MatchUserService matchUserService;
    private final VoteOptionRepository voteOptionRepository;

    public PostMainPageResponse upload(PostUploadRequest request, Member member) {
        Post entity = PostConverter.requestToEntity(request, member);
        Post savedPost = postRepository.save(entity);

        saveVoteOptions(request.matchUserIds(), savedPost);

        return PostMainPageResponse.entityToResponse(savedPost);
    }

    private void saveVoteOptions(List<Long> matchUserIds, Post post) {
        List<VoteOptions> voteOptions = matchUserIds.stream()
                .map(matchUserService::findById)
                .map(
                        matchUser -> {
                            return VoteOptions.builder()
                                    .matchUser(matchUser)
                                    .post(post)
                                    .build();
                        }
                )
                .toList();
        List<VoteOptions> savedVoteOptions = voteOptionRepository.saveAll(voteOptions);
        post.addVoteOptions(voteOptions);
    }

    @Transactional
    public String delete(Long postId, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                            log.error(PostStatus.POST_NOT_FOUND.getMessage());
                            return new NotFoundException(PostStatus.POST_NOT_FOUND);
                        }
                );
        if (member.getId()
                .equals(post.getMember()
                        .getId())) {
            postRepository.delete(post);
            return "Delete Complete";
        }
        log.error(PostStatus.UNAUTHORIZED_REQUEST.getMessage());
        throw new UnauthorizedException(PostStatus.UNAUTHORIZED_REQUEST);
    }
}

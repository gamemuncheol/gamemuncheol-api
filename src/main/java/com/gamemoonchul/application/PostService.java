package com.gamemoonchul.application;

import com.gamemoonchul.application.converter.PostConverter;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.domain.model.dto.VoteRate;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.repository.VoteOptionRepository;
import com.gamemoonchul.infrastructure.web.dto.PostResponseDto;
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

    public PostResponseDto upload(PostUploadRequest request, Member member) {
        Post entity = PostConverter.requestToEntity(request, member);
        Post savedPost = postRepository.save(entity);

        savedPost = saveVoteOptions(request.matchUserIds(), savedPost);
        List<VoteRate> voteRates = voteOptionRepository.getVoteRateByPostId(savedPost.getId());

        PostResponseDto response = PostResponseDto.entityToResponse(savedPost, voteRates);

        return response;
    }

    private Post saveVoteOptions(List<Long> matchUserIds, Post post) {
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
        return post;
    }

    @Transactional
    public String delete(Long postId, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                            log.error(PostStatus.POST_NOT_FOUND.getMessage());
                            return new BadRequestException(PostStatus.POST_NOT_FOUND);
                        }
                );
        if (member.getId()
                .equals(post.getMember()
                        .getId())) {
            postRepository.delete(post);
            return "Delete Complete";
        }
        log.error(PostStatus.UNAUTHORIZED_REQUEST.getMessage());
        throw new BadRequestException(PostStatus.UNAUTHORIZED_REQUEST);
    }
}

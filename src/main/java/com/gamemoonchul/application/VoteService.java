package com.gamemoonchul.application;

import com.gamemoonchul.application.post.PostService;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.Vote;
import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.infrastructure.repository.VoteRepository;
import com.gamemoonchul.infrastructure.web.dto.request.VoteCreateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {
    private final VoteOptionService voteOptionService;
    private final PostService postService;
    private final VoteRepository voteRepository;

    @Transactional
    public Vote createVote(VoteCreateRequest voteCreateRequest, Member member) {
        Post post = postService.findById(voteCreateRequest.postId());
        VoteOptions voteOption = voteOptionService.findByIdAndPostId(voteCreateRequest.voteOptionId(), voteCreateRequest.postId());

        Optional<Vote> savedVote = voteRepository.findByPostIdAndMemberId(voteCreateRequest.postId(), member.getId());

        if (savedVote.isPresent()) {
            Vote vote = savedVote.get();
            vote.updateVoteOption(voteOption);
            Double newVoteRatio = post.getMinVoteRatio();
            post.updateVoteRatio(newVoteRatio);
            return vote;
        }

        Vote vote = voteRepository.save(Vote.builder()
                .post(post)
                .voteOption(voteOption)
                .member(member)
                .build());
        Double newVoteRatio = post.getMinVoteRatio();
        post.updateVoteRatio(newVoteRatio);
        return vote;
    }
}

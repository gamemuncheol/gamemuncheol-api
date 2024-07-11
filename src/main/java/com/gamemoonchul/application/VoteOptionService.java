package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.NotFoundException;
import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.domain.status.VoteOptionStatus;
import com.gamemoonchul.infrastructure.repository.VoteOptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteOptionService {
    private final VoteOptionRepository voteOptionRepository;

    public VoteOptions findByIdAndPostId(Long voteOptionId, Long postId) {
        return voteOptionRepository.findByIdAndPostId(voteOptionId, postId)
                .orElseThrow(() -> new NotFoundException(VoteOptionStatus.VOTE_OPTION_NOT_FOUND));
    }
}

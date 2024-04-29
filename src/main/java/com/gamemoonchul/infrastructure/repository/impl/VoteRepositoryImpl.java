package com.gamemoonchul.infrastructure.repository.impl;

import com.gamemoonchul.domain.entity.Vote;
import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.infrastructure.repository.ifs.VoteRepositoryIfs;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.gamemoonchul.domain.entity.QPost.post;
import static com.gamemoonchul.domain.entity.QVote.vote;
import static com.gamemoonchul.domain.entity.QVoteOptions.voteOptions;
import static com.gamemoonchul.domain.entity.riot.QMatchUser.matchUser;

@Repository
public class VoteRepositoryImpl implements VoteRepositoryIfs {
    JPAQueryFactory queryFactory;

    public VoteRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Vote> searchVoteByPostIdAndVoteUserId(Long postId, Long voteUserId) {
        return Optional.empty();
    }

    @Override
    public HashMap<VoteOptions, Integer> getVoteRateByPostId(Long postId) {
        List<Vote> searchedVotes = queryFactory.select(vote)
                .from(vote)
                .join(vote.post, post)
                .fetchJoin()
                .join(vote.voteOptions, voteOptions)
                .fetchJoin()
                .join(voteOptions.matchUser, matchUser)
                .fetchJoin()
                .where(post.id.eq(postId))
                .fetch()
                ;

        HashMap<VoteOptions, Integer> voteRateHashMap = new HashMap<>();
        int sum = 0;
        for (Vote v: searchedVotes) {
            int curVal = voteRateHashMap.getOrDefault(v.getVoteOptions(), 0);
            voteRateHashMap.put(v.getVoteOptions(), curVal + 1);
            sum++;
        }

        for (VoteOptions vo: voteRateHashMap.keySet()) {
            voteRateHashMap.put(vo, voteRateHashMap.get(vo) * 100 / sum);
        }

        return voteRateHashMap;
    }
}

package com.gamemoonchul.infrastructure.repository.impl;

import com.gamemoonchul.domain.entity.QMember;
import com.gamemoonchul.domain.entity.QPost;
import com.gamemoonchul.domain.entity.QVoteOptions;
import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.domain.model.dto.QVoteRate;
import com.gamemoonchul.domain.model.dto.VoteRate;
import com.gamemoonchul.infrastructure.repository.ifs.VoteOptionRepositoryIfs;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gamemoonchul.domain.entity.QPost.post;
import static com.gamemoonchul.domain.entity.QVote.vote;
import static com.gamemoonchul.domain.entity.QVoteOptions.voteOptions;
import static com.gamemoonchul.domain.entity.riot.QMatchUser.matchUser;

@Repository
public class VoteOptionRepositoryImpl implements VoteOptionRepositoryIfs {
    JPAQueryFactory queryFactory;

    public VoteOptionRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<VoteOptions> searchByPostId(Long searchPostId) {
        return queryFactory.select(voteOptions)
                .from(voteOptions)
                .join(voteOptions.post, post)
                .fetchJoin()
                .join(voteOptions.matchUser, matchUser)
                .fetchJoin()
                .where(post.id.eq(searchPostId))
                .fetch();
    }

    @Override
    public List<VoteRate> getVoteRateByPostId(Long postId) {
        List<VoteRate> results = queryFactory
                .select(
                        new QVoteRate(
                                matchUser.id,
                                matchUser.nickname,
                                matchUser.championThumbnail,
                                voteOptions.id,
                                vote.count()
                        )
                )
                .from(voteOptions)
                .join(voteOptions.matchUser, matchUser)
                .leftJoin(vote)
                .on(voteOptions.id.eq(vote.voteOptions.id))
                .where(voteOptions.post.id.eq(postId))
                .groupBy(voteOptions.id)
                .fetch();
        int sum = results.stream()
                .map(VoteRate::getRate)
                .mapToInt(Long::intValue)
                .sum();

        results.forEach(voteRate -> {
            int rate = (int) ((double) voteRate.getRate() / sum * 100);
            voteRate.setRate((long) rate);
        });

        return results;
    }

}

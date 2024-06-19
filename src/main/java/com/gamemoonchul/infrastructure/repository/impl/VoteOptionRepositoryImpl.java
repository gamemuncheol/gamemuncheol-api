package com.gamemoonchul.infrastructure.repository.impl;

import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.infrastructure.repository.ifs.VoteOptionRepositoryIfs;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gamemoonchul.domain.entity.QPost.post;
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
                .join(post)
                .on(voteOptions.post.id.eq(post.id))
                .fetchJoin()
                .join(matchUser)
                .on(voteOptions.matchUser.id.eq(matchUser.id))
                .fetchJoin()
                .where(voteOptions.post.id.eq(searchPostId))
                .fetch();
    }

}

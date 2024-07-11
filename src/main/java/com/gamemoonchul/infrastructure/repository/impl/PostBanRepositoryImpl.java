package com.gamemoonchul.infrastructure.repository.impl;

import com.gamemoonchul.domain.entity.PostBan;
import com.gamemoonchul.infrastructure.repository.ifs.PostBanRepositoryIfs;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gamemoonchul.domain.entity.QPost.post;
import static com.gamemoonchul.domain.entity.QPostBan.postBan;

@Repository
public class PostBanRepositoryImpl implements PostBanRepositoryIfs {
    private JPAQueryFactory queryFactory;

    public PostBanRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PostBan> searchByMemberId(Long memberId) {
        return queryFactory.selectFrom(postBan)
                .join(postBan.banPost, post).fetchJoin()
                .where(postBan.member.id.eq(memberId))
                .fetch();
    }
}

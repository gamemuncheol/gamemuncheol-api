package com.gamemoonchul.infrastructure.repository.impl;

import com.gamemoonchul.domain.entity.MemberBan;
import com.gamemoonchul.infrastructure.repository.ifs.MemberBanRepositoryIfs;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gamemoonchul.domain.entity.QMember.member;
import static com.gamemoonchul.domain.entity.QMemberBan.memberBan;

@Repository
public class MemberBanRepositoryImpl implements MemberBanRepositoryIfs {
    private JPAQueryFactory queryFactory;

    public MemberBanRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberBan> searchByMemberId(Long memberId) {
        return queryFactory.selectFrom(memberBan)
                .join(memberBan.member, member).fetchJoin()
                .join(memberBan.banMember, member).fetchJoin()
                .where(member.id.eq(memberId))
                .fetch();
    }
}

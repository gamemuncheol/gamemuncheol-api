package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberBan;
import com.gamemoonchul.infrastructure.repository.ifs.MemberBanRepositoryIfs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberBanRepository extends JpaRepository<MemberBan, Long>, MemberBanRepositoryIfs {
    List<MemberBan> findAllByMember(Member member);

    Optional<MemberBan> findByMemberAndBanMember(Member member, Member banMember);

    Integer countByMemberId(Long memberId);
}

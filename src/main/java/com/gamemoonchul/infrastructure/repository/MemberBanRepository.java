package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberBan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberBanRepository extends JpaRepository<MemberBan, Long> {
    List<MemberBan> findAllByMember(Member member);
}

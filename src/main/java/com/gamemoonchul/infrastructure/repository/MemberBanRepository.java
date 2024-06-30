package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.MemberBan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBanRepository extends JpaRepository<MemberBan, Long> {
}

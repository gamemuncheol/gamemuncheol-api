package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.PostBan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostBanRepository extends JpaRepository<PostBan, Long> {
    List<PostBan> findAllByMember(Member member);
}

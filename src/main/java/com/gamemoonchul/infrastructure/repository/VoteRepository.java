package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    void deleteAllByMember(Member member);

    List<Vote> findByMember(Member member);
}

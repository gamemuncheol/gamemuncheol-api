package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByCreatedAt(Pageable pageable);

    Page<Post> findByVoteRatioGreaterThanEqual(Double ratio, Pageable pageable);

    Page<Post> findAllByOrderByViewCountDesc(Pageable pageable);

    void deleteAllByMember(Member member);

    List<Post> findByMember(Member member);
}

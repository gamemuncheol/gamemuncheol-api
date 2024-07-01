package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 최신
    Page<Post> findAllByIdNotInOrderByCreatedAtDesc(List<Long> postsIds, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 불판
    Page<Post> findAllByIdNotInAndVoteRatioGreaterThanEqual(List<Long> postIds, Double ratio, Pageable pageable);

    Page<Post> findAllByVoteRatioGreaterThanEqual(Double ratio, Pageable pageable);

    // Hot
    Page<Post> findAllByIdNotInOrderByViewCountDesc(List<Long> postsIds, Pageable pageable);

    Page<Post> findAllByOrderByViewCountDesc(Pageable pageable);

    void deleteAllByMember(Member member);

    List<Post> findByMember(Member member);
}

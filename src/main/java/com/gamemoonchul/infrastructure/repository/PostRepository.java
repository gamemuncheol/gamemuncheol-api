package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByIdLessThanOrderByCreatedAtDesc(Long id, Pageable pageable);

    Page<Post> findByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findByVoteRatioGreaterThanEqualAndVoteRatioLessThanOrderByVoteRatioDescVoteCountDesc(Double ratio, Double cursor, Pageable pageable);
}

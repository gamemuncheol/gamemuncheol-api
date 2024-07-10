package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.PostView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostViewRepository extends JpaRepository<PostView, Long> {
    List<PostView> findByCreatedAtAfterAndPostId(LocalDateTime createdAt, Long post_id);
}

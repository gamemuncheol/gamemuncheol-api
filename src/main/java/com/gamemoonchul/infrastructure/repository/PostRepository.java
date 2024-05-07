package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM POST p JOIN FETCH p.member ORDER BY p.createdAt DESC")
    Page<Post> findAllByOrderByCreatedAt(Pageable pageable);
}

package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> { }

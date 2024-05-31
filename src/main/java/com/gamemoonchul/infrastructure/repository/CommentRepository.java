package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.infrastructure.repository.ifs.CommentRepositoryIfs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryIfs {
}

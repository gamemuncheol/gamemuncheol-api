package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.repository.ifs.CommentRepositoryIfs;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryIfs {
    List<Comment> findByParentId(Long parentId);

    List<Comment> findAllByMember(Member member);

    List<Comment> findAllByPost(Post post);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = "5000"),  // 5초 타임아웃
    })
    @Query("select c from comment c where c.id = :id")
    Optional<Comment> findByIdForUpdate(@Param("id") Long id);
}

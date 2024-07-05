package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.repository.ifs.PostRepositoryIfs;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends PostRepositoryIfs, JpaRepository<Post, Long> {
    void deleteAllByMember(Member member);

    List<Post> findByMember(Member member);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = "5000"),  // 5초 타임아웃
            @QueryHint(name = "org.hibernate.cacheable", value = "false")  // 캐시 사용 안함
    })
    @Query("SELECT p FROM Post p WHERE p.id = :postId")
    Optional<Post> findByIdForUpdate(@Param("postId") Long postId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE post SET comment_count = comment_count + 1 WHERE id = :postId", nativeQuery = true)
    void incrementCommentCountNative(@Param("postId") Long postId);
}

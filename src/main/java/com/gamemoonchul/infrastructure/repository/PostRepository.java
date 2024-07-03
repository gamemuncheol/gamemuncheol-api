package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.repository.ifs.PostRepositoryIfs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PostRepositoryIfs, JpaRepository<Post, Long> {
    // 최신
    Page<Post> findAllByIdNotInOrderByCreatedAtDesc(List<Long> postsIds, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 불판
    Page<Post> findAllByIdNotInAndVoteRatioGreaterThanEqual(List<Long> postIds, Double ratio, Pageable pageable);

    Page<Post> findAllByVoteRatioGreaterThanEqual(Double ratio, Pageable pageable);

    void deleteAllByMember(Member member);

    List<Post> findByMember(Member member);
}

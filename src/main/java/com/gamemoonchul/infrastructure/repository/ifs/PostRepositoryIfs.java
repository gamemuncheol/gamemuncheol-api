package com.gamemoonchul.infrastructure.repository.ifs;

import com.gamemoonchul.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostRepositoryIfs {
    Optional<Post> searchByPostId(Long postId);

    Page<Post> searchGrillPostsWithoutBanPosts(Long memberId, Pageable pageable);

    Page<Post> searchNewPostsWithoutBanPosts(Long memberId, Pageable pageable);

    Page<Post> searchHotPostWithoutBanPosts(Long memberId, Pageable pageable);
}

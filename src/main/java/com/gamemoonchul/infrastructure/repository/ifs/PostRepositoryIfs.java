package com.gamemoonchul.infrastructure.repository.ifs;

import com.gamemoonchul.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryIfs {
    Page<Post> searchHotPostWithoutBanPosts(Long memberId, Pageable pageable);
}

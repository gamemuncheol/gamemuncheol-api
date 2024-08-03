package com.gamemoonchul.application;

import com.gamemoonchul.application.post.PostService;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.PostBan;
import com.gamemoonchul.infrastructure.repository.PostBanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostBanService {
    private final PostBanRepository postBanRepository;
    private final PostService postService;

    public void ban(Member member, Long postId) {
        Post post = postService.findById(postId);
        PostBan postBan = PostBan.builder()
                .banPost(post)
                .member(member)
                .build();
        postBanRepository.save(postBan);
    }

    public List<PostBan> bannedPost(Long id) {
        return postBanRepository.searchByMemberId(id);
    }
}

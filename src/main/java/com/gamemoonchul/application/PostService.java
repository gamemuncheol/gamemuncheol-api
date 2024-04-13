package com.gamemoonchul.application;

import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.converter.PostConverter;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.dto.PostResponseDto;
import com.gamemoonchul.infrastructure.web.dto.PostUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostResponseDto upload(PostUploadRequest request,
                                  Member member) {
        Post entity = PostConverter.requestToEntity(request, member);
        Post saved = postRepository.save(entity);
        PostResponseDto response = PostConverter.toResponse(saved);

        return response;
    }
}

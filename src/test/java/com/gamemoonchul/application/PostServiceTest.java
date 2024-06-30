package com.gamemoonchul.application;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.common.exception.UnauthorizedException;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberDummy;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.PostDummy;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.entity.riot.MatchUserDummy;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.MatchUserRepository;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.web.dto.response.PostResponseDto;
import com.gamemoonchul.infrastructure.web.dto.request.PostUploadRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PostServiceTest extends TestDataBase {
    @Autowired
    private PostService postService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MatchUserRepository matchUserRepository;


    @Test
    @DisplayName("게시물 업로드 테스트 ")
    public void defaultUploadTest() {
        // given
        Member member = memberRepository.save(MemberDummy.create());
        // match user 생성
        List<MatchUser> matchUsers = new ArrayList<>(List.of(MatchUserDummy.createDummy("1"), MatchUserDummy.createDummy("2")));
        List<MatchUser> savedMatchUsers = matchUserRepository.saveAll(matchUsers);
        // request 생성
        PostUploadRequest request = PostDummy.createRequest(savedMatchUsers.stream()
                .map(
                        MatchUser::getId
                )
                .collect(Collectors.toList()));

        // when
        PostResponseDto response = postService.upload(request, member);
        List<Post> allSavedPost = postRepository.findAll();

        // then
        assertThat(allSavedPost
                .size()).isEqualTo(1);
        assertThat(response.getCreatedAt()).isNotNull();
        assertThat(response.getUpdatedAt()).isNotNull();
        assertThat(response.getVoteOptionDetails()
                .size()).isEqualTo(2);
        assertThat(response.getViewCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("권한 없는 사용자가 삭제 요청했을 경우 에러 확인")
    public void unauthorizedDeleteTest() {
        // given
        Member member1 = MemberDummy.createWithId("1");
        Member member2 = MemberDummy.createWithId("2");
        memberRepository.saveAll(List.of(member1, member2));
        PostUploadRequest request = PostDummy.createRequest();

        // when
        PostResponseDto response = postService.upload(PostDummy.createRequest(), member1);

        // then
        assertThatThrownBy(() -> postService.delete(response.getId(), member2)).isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining(PostStatus.UNAUTHORIZED_REQUEST.getMessage());
    }
}
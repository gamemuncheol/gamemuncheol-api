package com.gamemoonchul.infrastructure.repository.impl;

import com.gamemoonchul.domain.entity.*;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.entity.riot.MatchUserDummy;
import com.gamemoonchul.infrastructure.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class VoteRepositoryImplTest {
    @Autowired
    private VoteOptionRepository voteOptionRepository;

    @Autowired
    private MatchUserRepository matchUserRepository;

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private EntityManager em;
    
    private List<Member> members;

    private Post post;
    private List<MatchUser> matchUsers = new ArrayList<MatchUser>();
    private List<VoteOptions> voteOptions = new ArrayList<VoteOptions>();

    @BeforeEach
    void setUp() {
        post = PostDummy.createPost("String1");
        matchUsers.add(MatchUserDummy.createDummy("232123123"));
        matchUsers.add(MatchUserDummy.createDummy("4387931221"));
        matchUserRepository.saveAll(matchUsers);
        postRepository.save(post);
                voteOptions.add(VoteOptions.builder()
                .post(post)
                .matchUser(matchUsers.get(0))
                .build());
        voteOptions.add(VoteOptions.builder()
                .post(post)
                .matchUser(matchUsers.get(1))
                .build());
        voteOptionRepository.saveAll(voteOptions);
    }

    @Test
    @DisplayName("임의의 멤버로 투표를 진행했을 때 투표의 결과가 정확히 실행한 투표의 결과와 일치하는지 테스트")
    void 투표50건테스트() {
        // given
        members = MemberDummy.createUserRoleMembers(50);
        members = memberRepository.saveAll(members);

        // when
        List<Vote> votes = new ArrayList<>();
        List<Vote> savedVotes = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Member tmp = members.get(i);
            savedVotes.add(voteRepository.save(Vote.builder()
                    .post(post)
                    .voteOptions(voteOptions.get(i % 2))
                    .member(tmp)
                    .build()));
        }
        HashMap<VoteOptions, Integer> voteRate = voteRepository.getVoteRateByPostId(post.getId());

        // then
        voteRate.values().forEach(v -> assertThat(v).isEqualTo(50));
    }
}
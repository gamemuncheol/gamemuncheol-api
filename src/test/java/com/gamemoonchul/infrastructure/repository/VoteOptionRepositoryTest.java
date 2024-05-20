package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.domain.entity.*;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.entity.riot.MatchUserDummy;
import com.gamemoonchul.domain.model.dto.VoteRate;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class VoteOptionRepositoryTest extends TestDataBase {
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


    private Post post;
    private List<MatchUser> matchUsers = new ArrayList<MatchUser>();
    private List<Member> members;
    private List<VoteOptions> voteOptions = new ArrayList<>();

    @BeforeEach
    void setUp() {
        post = PostDummy.createPost("String1");
        post = postRepository.save(post);

        matchUsers.add(MatchUserDummy.createDummy("232123123"));
        matchUsers.add(MatchUserDummy.createDummy("4387931221"));
        matchUserRepository.saveAll(matchUsers);

        VoteOptions vote1 = VoteOptions.builder()
                .post(post)
                .matchUser(matchUsers.get(0))
                .build();
        VoteOptions vote2 = VoteOptions.builder()
                .post(post)
                .matchUser(matchUsers.get(1))
                .build();

        voteOptions.add(vote1);
        voteOptions.add(vote2);

        voteOptionRepository.saveAll(voteOptions);
    }

    @Test
    @DisplayName("포스트 ID로 VoteOptions잘 가져올 수 있는지 테스트")
    @Transactional
    void getVoteRateByPostId() {
        // given // when
        List<VoteOptions> voteRates = voteOptionRepository.searchByPostId(post.getId());

        // then
        assertThat(voteRates.size()).isEqualTo(2);
        assertThat(voteRates.get(0)
                .getPost()
                .getId()).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("임의의 멤버로 투표를 진행했을 때 투표의 결과가 정확히 실행한 투표의 결과와 일치하는지 테스트")
    void 투표50건테스트() {
        // given
        members = MemberDummy.createUserRoleMembers(50);
        members = memberRepository.saveAll(members);
        List<Vote> savedVotes = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            savedVotes.add(voteRepository.save(Vote.builder()
                    .post(post)
                    .voteOptions(voteOptions.get(i % 2))
                    .member(members.get(i))
                    .build()));
        }


        // when
        List<VoteRate> voteRates = voteOptionRepository.getVoteRateByPostId(post.getId());

        // then
        voteRates.forEach(
                voteRate -> {
                    AssertionsForClassTypes.assertThat(voteRate.getRate())
                            .isEqualTo(50);
                }
        );
    }
}
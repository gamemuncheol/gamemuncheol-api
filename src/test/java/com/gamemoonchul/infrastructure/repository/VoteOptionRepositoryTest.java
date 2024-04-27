package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.entity.PostDummy;
import com.gamemoonchul.domain.entity.VoteOptions;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.entity.riot.MatchUserDummy;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest @Transactional
class VoteOptionRepositoryTest {
    @Autowired
    private VoteOptionRepository voteOptionRepository;

    @Autowired
    private MatchUserRepository matchUserRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityManager em;

    private Post post;
    private List<MatchUser> matchUsers = new ArrayList<MatchUser>();

    @BeforeEach
    void setUp() {
        post = PostDummy.createPost("String1");
        matchUsers.add(MatchUserDummy.createDummy("232123123"));
        matchUsers.add(MatchUserDummy.createDummy("4387931221"));
        matchUserRepository.saveAll(matchUsers);
        postRepository.save(post);
    }

    @Test
    @DisplayName("Vote Options matchUser, post 연관관계 잘 Join 되는지 확인")
    void voteOptionsJoinTest() {
        // given
        VoteOptions voteOptions1 = VoteOptions.builder()
                .post(post)
                .matchUser(matchUsers.get(0))
                .build();
                VoteOptions voteOptions2 = VoteOptions.builder()
                .post(post)
                .matchUser(matchUsers.get(1))
                .build();
        // when
        voteOptionRepository.save(voteOptions1);
        voteOptionRepository.save(voteOptions2);
        em.clear();
        List<VoteOptions> searchedOptions = voteOptionRepository.findByPost_Id(post.getId());

        // then
        assertThat(searchedOptions.size()).isEqualTo(2);
    }
}
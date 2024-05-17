package com.gamemoonchul.infrastructure.repository.impl;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.domain.entity.*;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.gamemoonchul.domain.entity.riot.MatchUserDummy;
import com.gamemoonchul.domain.model.dto.VoteRate;
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

class VoteRepositoryImplTest extends TestDataBase {
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


}
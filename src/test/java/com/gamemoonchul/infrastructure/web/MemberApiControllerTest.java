package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.config.jwt.*;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberDummy;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.web.common.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberApiControllerTest extends BaseIntegrationTest {
    @Autowired
    private TokenHelper tokenHelper;
    private TokenDto tokenDto;
    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    public void setUp() {
        member = MemberDummy.create();
        memberRepository.save(member);
        TokenInfo tokenInfo = TokenInfoDummy.createRefresh();
        tokenDto = tokenHelper.generateToken(tokenInfo);
    }

    @Test
    @DisplayName("닉네임 변경 테스트")
    void nicknameChangeTest() throws Exception {
        // given
        String nickname = UUID.randomUUID().toString().substring(0, 8);

        // when
        ResultActions resultActions = super.mvc.perform(patch("/api/member/change-nickname/" + nickname).header("Authorization", "Bearer " + tokenDto.getAccessToken()));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("내 정보 조회 테스트")
    void meTest() throws Exception {
        // given

        // when
        ResultActions resultActions = super.mvc.perform(get("/api/member/me").header("Authorization", "Bearer " + tokenDto.getAccessToken()));

        // then
        resultActions.andExpect(jsonPath("$.data.email").value(member.getEmail()));
    }

    @Test
    @DisplayName("존재하지 않는 회원 조회시 예외 발생 테스트")
    void meFailTest() throws Exception {
        // given
        String accessToken = tokenDto.getAccessToken();
        memberRepository.delete(member);

        // when
        ResultActions resultActions = super.mvc.perform(get("/api/member/me").header("Authorization", "Bearer " + accessToken));

        // then
        resultActions.andExpect(status().isForbidden());
    }
}
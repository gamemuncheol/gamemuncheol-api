package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.config.jwt.TokenHelper;
import com.gamemoonchul.config.jwt.TokenInfo;
import com.gamemoonchul.config.jwt.TokenType;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberDummy;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.web.common.BaseIntegrationTest;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application.yaml")
class MemberPrivacyControllerTest extends BaseIntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenHelper tokenHelper;

    Member member;

    TokenDto tokenDto;

    @Test
    @Order(1)
    void setUp() {
        member = MemberDummy.createPrivacyRole();
        memberRepository.save(member);
    }

    void getTokenDto() {
        member = MemberDummy.createPrivacyRole();
                TokenInfo tokenInfo = TokenInfo.builder()
                .email(member.getEmail())
                .provider(member.getProvider().toString())
                .identifier(member.getIdentifier())
                .tokenType(TokenType.ACCESS)
                .build();
        tokenDto = tokenHelper.generateToken(tokenInfo);
    }

    @Test
    @Order(2)
    @DisplayName("privacy 동의 안된 경우 테스트")
    void notAgreed() throws Exception {
        // given
        getTokenDto();
        // when
        ResultActions resultActions = super.mvc.perform(get("/privacy/is-agreed")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenDto.getAccessToken()));

        // then
        resultActions.andExpect(jsonPath("$.data").value(false));
    }

    @Test
    @Order(3)
    @DisplayName("privacy 동의 api 호출")
    void agreePrivcayTest() throws Exception {
        // given
        getTokenDto();
        // when
        ResultActions resultActions = super.mvc.perform(MockMvcRequestBuilders.patch("/privacy/agree")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenDto.getAccessToken()));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("privacy 동의 후 동의가 됐음을 확인")
    void agreed() throws Exception {
        // given
        getTokenDto();
        // when
        ResultActions resultActions = super.mvc.perform(get("/privacy/is-agreed")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenDto.getAccessToken()));

        // then
        resultActions.andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @Order(2)
    @DisplayName("토큰 없이 호출하면 401에러 발생")
    void notAuthorized() throws Exception {
        // given // when
        ResultActions resultActions = super.mvc.perform(get("/privacy/is-agreed")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}

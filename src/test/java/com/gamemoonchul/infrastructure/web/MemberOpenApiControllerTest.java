package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.config.jwt.*;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.infrastructure.web.dto.request.RenewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MemberOpenApiControllerTest extends TestDataBase {

    @Autowired
    private TokenHelper tokenHelper;

    @Test
    @DisplayName("토큰 재발급 테스트")
    void tokenRenewTest() throws Exception {
        // given
        TokenInfo tokenInfo = TokenInfoDummy.createRefresh();
        TokenDto tokenDto = tokenHelper.generateToken(tokenInfo);
        RenewRequest renewRequest = new RenewRequest(tokenDto.getRefreshToken());

        // when
        ResultActions resultActions =
                super.mvc.perform(post("/open-api/members/renew")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(super.objectMapper.writeValueAsString(renewRequest))
                                .accept(MediaType.APPLICATION_JSON)
                        )
                        .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists());
    }

    @Test
    @DisplayName("액세스 토큰으로 재발급 실패하는지 테스트")
    void tokenRenewFailTest() throws Exception {
        // given
        TokenInfo tokenInfo = TokenInfo.builder()
                .email("test@gmail.com")
                .identifier("test")
                .provider(OAuth2Provider.GOOGLE.toString())
                .tokenType(TokenType.REFRESH)
                .build();
        TokenDto tokenDto = tokenHelper.generateToken(tokenInfo);
        RenewRequest renewRequest = new RenewRequest(tokenDto.getAccessToken());

        // when
        ResultActions resultActions;
        try {
            resultActions =
                    super.mvc.perform(post("/open-api/members/renew")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(super.objectMapper.writeValueAsString(renewRequest))
                                    .accept(MediaType.APPLICATION_JSON)
                            )
                            .andDo(print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // then
        resultActions.andExpect(status().is4xxClientError());
    }

}
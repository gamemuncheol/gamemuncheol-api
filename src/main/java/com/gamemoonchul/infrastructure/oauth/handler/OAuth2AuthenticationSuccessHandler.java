package com.gamemoonchul.infrastructure.oauth.handler;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.common.util.CookieUtils;
import com.gamemoonchul.infrastructure.jwt.TokenProvider;
import com.gamemoonchul.infrastructure.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.gamemoonchul.infrastructure.oauth.OAuth2UserPrincipal;
import com.gamemoonchul.infrastructure.oauth.Oauth2Status;
import com.gamemoonchul.infrastructure.oauth.user.OAuth2Provider;
import com.gamemoonchul.infrastructure.oauth.user.OAuth2UserUnlinkManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static com.gamemoonchul.infrastructure.oauth.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;
import static com.gamemoonchul.infrastructure.oauth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;


// 인증에 성공했을 경우를 담당
@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        // 인증 성공 후 리다이렉트될 대상 URL을 결정
        String targetUrl;
        targetUrl = determineTargetUrl(request, response, authentication);

        // 응답이 이미 커밋된 경우 리다이렉트를 수행할 수 없으므로 로그 남기고 종료
        if (response.isCommitted()) {
            throw new ApiException(Oauth2Status.EXPIRED_LOGIN);
        }

        clearAuthenticationAttributes(request, response);
        // 리다이렉트 수행
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // 인증 성공 후 리다이렉트될 대상 URL을 결정
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String mode = CookieUtils.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("");

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);

        if (principal == null) {
            throw new ApiException(Oauth2Status.LOGIN_FAILED);
        }

        if ("login".equalsIgnoreCase(mode)) {
            // TODO: DB 저장
            // TODO: 리프레시 토큰 DB 저장
            log.info("email={}, name={}, nickname={}, accessToken={}", principal.getUserInfo().getEmail(),
                    principal.getUserInfo().getName(),
                    principal.getUserInfo().getNickname(),
                    principal.getUserInfo().getAccessToken()
            );

            String accessToken = tokenProvider.createToken(authentication, TokenProvider.TokenType.ACCESS_TOKEN);
            String refreshToken = tokenProvider.createToken(authentication, TokenProvider.TokenType.REFRESH_TOKEN);

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .build().toUriString();

        } else if ("unlink".equalsIgnoreCase(mode)) {
            String accessToken = principal.getUserInfo().getAccessToken();
            OAuth2Provider provider = principal.getUserInfo().getProvider();

            // TODO: DB 삭제
            // TODO: 리프레시 토큰 삭제
            oAuth2UserUnlinkManager.unlink(provider, accessToken);

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .build().toUriString();
        }
        throw new ApiException(Oauth2Status.LOGIN_FAILED);
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

    /**
     * 사용자의 인증과정에서 생성된 임시데이터나 쿠키를 정리하는 역할을 수행
     * @param request
     * @param response
     */
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}

package com.gamemoonchul.infrastructure.oauth;

import com.gamemoonchul.common.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Oauth2 인증 과정 중에 state, redirect_uri 등의 정보를 어딘가에 저장해야하는데 이를 쿠키에 저장하는 방식을 구현한 사용자 클래스
 * Spring Bean으로 등록하고 SpringSecurityConfig에서 authorizationRequestRepository로 등록한다.
 * OAuth2AuthorizationRequestRedirectFilter와 OAuth2LoginAuthenticationFilter에서 인증 과정 중에 호출됨.
 * <p>
 * 최초에 프론트엔드에서 로그인 요청시 리다이렉트 할 Oauth 제공자 별 URL 정보를 쿠키에 저장해서 리다이렉트
 * 사용자 로그인 성공시 백엔드로 리다이렉트 될 때 인증 과정 및 사용자 정보 불러오는 과정을 마친 후 쿠키에 저장된 정보 삭제
 */
@RequiredArgsConstructor
@Component
public class HttpCookieOAuth2AuthorizationRequestRepository
    implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
  public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
  public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
  public static final String MODE_PARAM_COOKIE_NAME = "mode";
  private static final int COOKIE_EXPIRE_SECONDS = 180;

  /**
   * OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME를 찾아서 역직렬화해서 반환
   * 쿠키 없는 경우 null 반환
   * @param request
   * @return
   */
  @Override
  public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
    return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
        .orElse(null);
  }

  /**
   * OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME에 OAuth2AuthorizationRequest를 직렬화해서 저장
   * 쿠키에 저장할 때는 redirect_uri, mode도 같이 저장
   * @param authorizationRequest
   * @param request
   * @param response
   */
  @Override
  public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
                                       HttpServletResponse response) {
    if (authorizationRequest == null) {
      CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
      CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
      CookieUtils.deleteCookie(request, response, MODE_PARAM_COOKIE_NAME);
      return;
    }

    CookieUtils.addCookie(response,
        OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
        CookieUtils.serialize(authorizationRequest),
        COOKIE_EXPIRE_SECONDS);

    String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
    if (StringUtils.hasText(redirectUriAfterLogin)) {
      CookieUtils.addCookie(response,
          REDIRECT_URI_PARAM_COOKIE_NAME,
          redirectUriAfterLogin,
          COOKIE_EXPIRE_SECONDS);
    }

    String mode = request.getParameter(MODE_PARAM_COOKIE_NAME);
    if (StringUtils.hasText(mode)) {
      CookieUtils.addCookie(response,
          MODE_PARAM_COOKIE_NAME,
          mode,
          COOKIE_EXPIRE_SECONDS);
    }
  }

  /**
   * 현재 요청에 대한 인증 요청을 로드한 후 제거
   * @param request
   * @param response
   * @return
   */
  @Override
  public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
                                                               HttpServletResponse response) {
    return this.loadAuthorizationRequest(request);
  }

  /**
   * 인증과 관련된 모든 쿠키를 삭제
   * @param request
   * @param response
   */
  public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
    CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    CookieUtils.deleteCookie(request, response, MODE_PARAM_COOKIE_NAME);
  }
}

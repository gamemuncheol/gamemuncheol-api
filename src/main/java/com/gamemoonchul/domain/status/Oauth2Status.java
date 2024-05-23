package com.gamemoonchul.domain.status;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OauthStatus
 * Oauth 관련 상태 코드 1000 ~ 1999
 */
@Getter
@AllArgsConstructor
public enum Oauth2Status implements ApiStatusIfs {
  // 만료된 로그인임을 알림
  EXPIRED_LOGIN(1000, "만료된 로그인입니다."),
  LOGIN_FAILED(1001, "로그인에 실패하였습니다."),
  UNLINK_FAILED(1002, "연동 해제에 실패하였습니다."),
  NOT_FOUND_EMAIL(1003, "이메일을 찾을 수 없습니다."),
  UNKNOWN_EXCEPTION(1500, "알 수 없는 예외가 발생하였습니다."),
  NOT_FOUND_PROVIDER(1404, "지원하지 않는 제공자입니다."),
  ;
    private final Integer statusCode;
  private final String message;

}

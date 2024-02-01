package com.gamemoonchul.config.jwt;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JWT 상태 11000 ~ 11999
 */
@Getter
@AllArgsConstructor
public enum JwtStatus implements ApiStatusIfs {
  NOT_VALID_TOKEN(11000, "유효하지 않은 토큰입니다."),
  EXPIRED_TOKEN(11001, "만료된 토큰입니다."),
  SIGNATURE_NOT_MATCH(
      11002, "토큰의 서명이 일치하지 않습니다."),
  NULL_POINT(11003, "토큰이 존재하지 않습니다."),
  ;

  private final Integer statusCode;
  private final String message;
}
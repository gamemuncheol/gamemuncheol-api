package com.gamemoonchul.domain.status;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원 관련 상태 코드 2000 ~ 2999
 */
@Getter
@AllArgsConstructor
public enum MemberStatus implements ApiStatusIfs {
  MEMBER_NOT_FOUND(2404, "회원을 찾을 수 없습니다."),
  ALREADY_EXIST_NICKNAME(2405, "이미 존재하는 닉네임입니다."),
  ;

  private final Integer statusCode;
  private final String message;
}

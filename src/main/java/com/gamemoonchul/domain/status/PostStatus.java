package com.gamemoonchul.domain.status;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시글 관련 상태 코드 6000 ~ 6999
 */
@Getter
@AllArgsConstructor
public enum PostStatus implements ApiStatusIfs {
  POST_NOT_FOUND(6404, "게시글을 찾을 수 없습니다."),
  UNAUTHORIZED_REQUEST(6403, "권한없는 요청 입니다.")
  ;

  private final Integer statusCode;
  private final String message;
}

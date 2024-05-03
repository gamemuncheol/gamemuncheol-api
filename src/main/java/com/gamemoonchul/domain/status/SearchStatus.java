package com.gamemoonchul.domain.status;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원 관련 상태 코드 3000 ~ 3999
 */
@Getter
@AllArgsConstructor
public enum SearchStatus implements ApiStatusIfs {
  SEARCH_FAILED(3400, "검색에 실패하였습니다."),
  FAILED_PARSE_JSON(3503, "JSON 파싱에 실패하였습니다."),
  SEARCH_RESULT_NOT_FOUND(3404, "검색 결과를 찾을 수 없습니다."),



  ;

  private final Integer statusCode;
  private final String message;
}

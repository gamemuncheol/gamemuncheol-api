package com.gamemoonchul.domain.status;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유튜브 업로드 관련 상태코드
 * 5000 ~ 5999
 */
@Getter
@AllArgsConstructor
public enum YoutubeStatus implements ApiStatusIfs {
    JSON_PARSE_ERROR(5500, "JSON 파싱 에러"),
    YOUTUBE_API_ERROR(5501, "유튜브 API 에러"),
    UNKNOWN_ERROR(5502, "알 수 없는 에러"),
    ;

    private final Integer statusCode;
    private final String message;
}

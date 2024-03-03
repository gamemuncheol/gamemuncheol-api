package com.gamemoonchul.domain.status;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VideoStatus implements ApiStatusIfs {
    INVALID_FILETYPE(4400, "지원하지 않는 파일 형식입니다."),
    FILE_SIZE_EXCEEDED(4401, "파일 크기가 최대 크기를 초과하였습니다."),
    FILE_UPLOAD_FAILED(4502, "파일 업로드에 실패하였습니다."),
    EMPTY_FILE(4403, "파일이 비어있습니다."),
    ;
    private final Integer statusCode;
  private final String message;

}

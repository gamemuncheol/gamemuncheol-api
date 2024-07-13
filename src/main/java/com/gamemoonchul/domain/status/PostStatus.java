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
    UNAUTHORIZED_REQUEST(6403, "권한없는 요청 입니다."),
    WRONG_MATCH_USER(6402, "잘못된 투표 유저 입니다."),
    COMMENT_NOT_FOUND(6405, "댓글을 찾을 수 없습니다."),
    COMMENT_CANT_HAVE_GRANDMOTHER(6406, "대댓글 까지만 허용 됩니다."),
    INVALID_REPLY(6407, "부모와 자식 댓글이 Post 동일한 Post에서 작성되지 않았습니다.");

    private final Integer statusCode;
    private final String message;
}

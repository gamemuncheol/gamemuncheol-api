package com.gamemoonchul.infrastructure.web.dto.request;

import lombok.Getter;

public record CommentRequest(String content, Long postId) {
}

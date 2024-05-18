package com.gamemoonchul.infrastructure.web.dto;

import lombok.Getter;

public record CommentRequest(String content, Long postId) {
}

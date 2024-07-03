package com.gamemoonchul.infrastructure.repository.ifs;

import com.gamemoonchul.domain.entity.Comment;

public interface CommentRepositoryIfs {
    Comment searchByIdOrThrow(Long commentId);
}

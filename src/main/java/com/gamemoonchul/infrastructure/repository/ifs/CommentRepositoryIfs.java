package com.gamemoonchul.infrastructure.repository.ifs;

import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.entity.Member;

import java.util.List;

public interface CommentRepositoryIfs {
    Comment searchByIdOrThrow(Long commentId);

    List<Comment> searchByPostId(Long postId, Long memberId);
}

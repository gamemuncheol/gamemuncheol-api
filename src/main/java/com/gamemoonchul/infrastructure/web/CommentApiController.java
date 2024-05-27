package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.CommentService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.CommentFixRequest;
import com.gamemoonchul.infrastructure.web.dto.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestControllerWithEnvelopPattern
public class CommentApiController {
    private final CommentService commentService;

    @PutMapping
    public void save(CommentRequest request, @MemberSession Member member) {
        commentService.save(request, member);
    }

    @PatchMapping
    public void fix(CommentFixRequest request, @MemberSession Member member) {
        commentService.fix(request, member);
    }
}

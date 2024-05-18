package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.CommentService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestControllerWithEnvelopPattern
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/save")
    public void save(CommentRequest request, @MemberSession Member member) {
        commentService.save(request, member);
    }
}

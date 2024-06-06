package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.CommentService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.model.dto.CommentSaveDto;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.CommentFixRequest;
import com.gamemoonchul.infrastructure.web.dto.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/comments")
@RestControllerWithEnvelopPattern
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping
    public void save(@RequestBody CommentRequest request, @MemberSession Member member) {
        CommentSaveDto saveDto = new CommentSaveDto(null, request.content(), request.postId());
        commentService.save(saveDto, member);
    }

    @PostMapping("/{parentId}")
    public void save(
            @PathVariable(name = "parentId") Long parentId,
            @RequestBody CommentRequest request,
            @MemberSession Member member) {
        CommentSaveDto saveDto = new CommentSaveDto(parentId, request.content(), request.postId());
        commentService.save(saveDto, member);
    }


    @PatchMapping
    public void fix(@RequestBody CommentFixRequest request, @MemberSession Member member) {
        commentService.fix(request, member);
    }

    @DeleteMapping("/{id}")
    public void del(@PathVariable(name = "id") Long id, @MemberSession Member member) {
        commentService.delete(id, member);
    }
}

package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.VoteService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Vote;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.request.VoteCreateRequest;
import com.gamemoonchul.infrastructure.web.dto.response.VoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/votes")
@RestControllerWithEnvelopPattern
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteResponse save(
            @RequestBody VoteCreateRequest voteCreateRequest,
            @MemberSession Member member
    ) {
        Vote vote = voteService.createVote(voteCreateRequest, member);
        return VoteResponse.entityToResponse(vote);
    }
}

package com.gamemoonchul.application.member;

import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.CommentRepository;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDeactivateService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public void deactivateAccount(OAuth2Provider provider, String identifier) {
        Optional<Member> member = memberRepository.findByProviderAndIdentifier(provider, identifier);
        if (member.isEmpty()) {
            throw new BadRequestException(MemberStatus.MEMBER_NOT_FOUND);
        }
        commentRepository.deleteAll(requiredDeleteComments(member.get()));
        memberRepository.delete(member.get());
    }

    // 내가 쓴 댓글에 단 대댓글들 전부 삭제
    private List<Comment> requiredDeleteComments(Member member) {
        List<Comment> comments = commentRepository.findAllByMember(member);

        List<Comment> requiredDelComments = new ArrayList<>(comments);
        comments.stream()
                .filter(it -> {
                    return !it.parentExist();
                })
                .forEach(it -> {
                    List<Comment> childComments = commentRepository.findByParentId(it.getId());
                    requiredDelComments.addAll(childComments);
                });
        return requiredDelComments;
    }
}

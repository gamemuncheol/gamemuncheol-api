package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.application.converter.MemberConverter;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.web.dto.MemberResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    public void delete(Member member) {
        memberRepository.delete(member);
    }

    public Optional<Member> findByProviderAndIdentifier(OAuth2Provider provider, String identifier) {
        return memberRepository.findTop1ByProviderAndIdentifier(provider, identifier);
    }

    public void deactivateAccount(String email, OAuth2Provider provider, String identifier) {
        Optional<Member> member = memberRepository.findTop1ByProviderAndIdentifier(provider, identifier);
        if (member.isEmpty()) {
            log.error(MemberStatus.MEMBER_NOT_FOUND.getMessage());
            throw new BadRequestException(MemberStatus.MEMBER_NOT_FOUND);
        }
        memberRepository.delete(member.get());
    }

    public void updateNickName(Member member, @NotEmpty @Max(10) String nickName) {
        if (isExistNickname(nickName)) {
            throw new BadRequestException(MemberStatus.ALREADY_EXIST_NICKNAME);
        }
        Member updatedMember = member.updateNickname(nickName);
        memberRepository.save(updatedMember);
    }

    private boolean isExistNickname(String nickName) {
        Optional<Member> savedMember = memberRepository.findByNickname(nickName);
        return savedMember.isPresent();
    }


    public MemberResponseDto me(Member member) {
        MemberResponseDto response = memberConverter.toResponseDto(member);
        return response;
    }
}

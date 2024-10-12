package com.gamemoonchul.application.member;

import com.gamemoonchul.application.converter.MemberConverter;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.web.dto.request.NicknameChangeRequest;
import com.gamemoonchul.infrastructure.web.dto.response.MemberResponse;
import jakarta.transaction.Transactional;
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

    public void delete(Member member) {
        memberRepository.delete(member);
    }

    public Optional<Member> findByProviderAndIdentifier(OAuth2Provider provider, String identifier) {
        return memberRepository.findByProviderAndIdentifier(provider, identifier);
    }

    public void updateNickName(Member member, NicknameChangeRequest request) {
        if (isExistNickname(request.nickname())) {
            throw new BadRequestException(MemberStatus.ALREADY_EXIST_NICKNAME);
        }
        Member updatedMember = member.updateNickname(request.nickname());
        memberRepository.save(updatedMember);
    }

    private boolean isExistNickname(String nickName) {
        Optional<Member> savedMember = memberRepository.findByNickname(nickName);
        return savedMember.isPresent();
    }


    public MemberResponse me(Member member) {
        MemberResponse response = MemberConverter.toResponseDto(member);
        return response;
    }
}

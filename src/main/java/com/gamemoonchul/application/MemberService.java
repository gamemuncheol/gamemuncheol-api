package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.config.jwt.TokenHelper;
import com.gamemoonchul.config.jwt.TokenInfo;
import com.gamemoonchul.config.jwt.TokenType;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.application.converter.MemberConverter;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.enums.MemberRole;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.web.dto.MemberResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenHelper tokenHelper;
    private final MemberConverter memberConverter;

    public void signIn(Member member) {
        Optional<Member> alreadyExistMember = memberRepository.findTop1ByProviderAndIdentifier(member.getProvider(), member.getIdentifier());
        if (alreadyExistMember.isEmpty()) {
            memberRepository.save(member);
        }
    }

    public void deactivateAccount(String email, OAuth2Provider provider, String identifier) {
        Optional<Member> member = memberRepository.findTop1ByProviderAndIdentifier(provider, identifier);
        if (member.isEmpty()) {
            log.error(MemberStatus.MEMBER_NOT_FOUND.getMessage());
            throw new BadRequestException(MemberStatus.MEMBER_NOT_FOUND);
        }
        memberRepository.delete(member.get());
    }

    public void updateNickName(Member member, String nickName) {
        if (validateNickname(nickName)) {
            log.error(MemberStatus.ALREADY_EXIST_NICKNAME.getMessage());
            throw new BadRequestException(MemberStatus.ALREADY_EXIST_NICKNAME);
        }
        Member updatedMember = member.updateNickname(nickName);
        memberRepository.save(updatedMember);
    }

    public boolean validateNickname(String nickName) {
        List<Member> savedMember = memberRepository.findByNickname(nickName);
        if(savedMember.isEmpty()) {
            return true;
        }
        return false;
    }

    public TokenDto renew(String refreshToken) {
        tokenHelper.validateToken(refreshToken, TokenType.REFRESH);
        TokenInfo tokenInfo = tokenHelper.getTokenInfo(refreshToken);
        TokenDto newToken = tokenHelper.generateToken(tokenInfo);
        return newToken;
    }

    public MemberResponseDto me(Member member) {
        MemberResponseDto response = memberConverter.toResponseDto(member);
        return response;
    }

    public MemberResponseDto privacyAgree(Member member) {
        member.setPrivacyAgreed(true);
        member.setPrivacyAgreedAt(LocalDateTime.now());
        member.setRole(MemberRole.USER);
        Member result = memberRepository.save(member);
        MemberResponseDto response = memberConverter.toResponseDto(result);
        return response;
    }
}

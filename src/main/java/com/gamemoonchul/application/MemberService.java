package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public void signInOrUp(Member member) {
    Optional<Member> alreadyExistMember = memberRepository.findTop1ByEmailAndProviderAndIdentifier(member.getEmail(), member.getProvider(), member.getIdentifier());
    if (alreadyExistMember.isEmpty()) {
      memberRepository.save(member);
    } else {
      member = alreadyExistMember.get();
    }
  }

  public void unlink(String email, OAuth2Provider provider, String identifier) {
    Optional<Member> member = memberRepository.findTop1ByEmailAndProviderAndIdentifier(email, provider, identifier);
    if (member.isEmpty()) {
      throw new ApiException(MemberStatus.MEMBER_NOT_FOUND);
    }
    memberRepository.delete(member.get());
  }

  public void updateNickNameOrThrow(String nickname) {
    Optional<Member> savedMember = memberRepository.findByNickname(nickname);
    if(savedMember.isPresent()) {
      throw new ApiException(MemberStatus.ALREADY_EXIST_NICKNAME);
    }
  }
}

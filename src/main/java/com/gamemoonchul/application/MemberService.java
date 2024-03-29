package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.MemberEntity;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  public void signInOrUp(MemberEntity member) {
    Optional<MemberEntity> alreadyExistMember = memberRepository.findTop1ByEmail(member.getEmail());
    if (alreadyExistMember.isEmpty()) {
      memberRepository.save(member);
    }
  }

  public void unlink(String email) {
    Optional<MemberEntity> member = memberRepository.findTop1ByEmail(email);
    if (member.isEmpty()) {
      throw new ApiException(MemberStatus.MEMBER_NOT_FOUND);
    }
    memberRepository.delete(member.get());
  }
}

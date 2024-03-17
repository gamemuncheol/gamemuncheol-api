package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
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
    Optional<Member> alreadyExistMember = memberRepository.findTop1ByEmail(member.getEmail());
    if (alreadyExistMember.isEmpty()) {
      memberRepository.save(member);
    } else {
      member = alreadyExistMember.get();
    }
  }

  public void unlink(String email) {
    Optional<Member> member = memberRepository.findTop1ByEmail(email);
    if (member.isEmpty()) {
      throw new ApiException(MemberStatus.MEMBER_NOT_FOUND);
    }
    memberRepository.delete(member.get());
  }
}

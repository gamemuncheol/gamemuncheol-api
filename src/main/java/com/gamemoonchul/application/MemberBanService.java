package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberBan;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberBanRepository;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberBanService {
    private final MemberBanRepository memberBanRepository;
    private final MemberRepository memberRepository;

    public void ban(Member member, Long banMemberId) {
        Member banMember = getBanMember(banMemberId);
        memberBanRepository.findByMemberAndBanMember(member, banMember)
                .orElseGet(() -> memberBanRepository.save(MemberBan.builder()
                        .member(member)
                        .banMember(banMember)
                        .build()));
    }

    public List<MemberBan> bannedMembers(Long id) {
        List<MemberBan> bannedMember = memberBanRepository.searchByMemberId(id);
        return bannedMember;
    }

    public void deleteBan(Member member, Long banMemberId) {
        Member banMember = getBanMember(banMemberId);

        memberBanRepository.findByMemberAndBanMember(member, banMember)
                .ifPresent(
                        memberBanRepository::delete
                );
    }

    private Member getBanMember(Long banMemberId) {
        Member banMember = memberRepository.findById(banMemberId)
                .orElseThrow(
                        () -> new BadRequestException(MemberStatus.MEMBER_NOT_FOUND)
                );
        return banMember;
    }

    public Integer countByMemberId(Long memberId) {
        return memberBanRepository.countByMemberId(memberId);
    }
}

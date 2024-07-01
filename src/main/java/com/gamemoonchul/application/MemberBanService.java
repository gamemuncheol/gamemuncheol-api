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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberBanService {
    private final MemberBanRepository memberBanRepository;
    private final MemberRepository memberRepository;

    public List<Long> getBanPostIds(Member member) {
        List<Long> banPostIds = new ArrayList<>();
        if (member != null) {
            List<MemberBan> memberBans = memberBanRepository.findAllByMember(member);
            for (MemberBan memberBan : memberBans) {
                memberBan.getBanMember()
                        .getPosts()
                        .forEach(post -> banPostIds.add(post.getId()));
            }
        }
        return banPostIds;
    }

    public void ban(Member member, Long banMemberId) {
        Member banMember = getBanMember(banMemberId);
        memberBanRepository.findByMemberAndBanMember(member, banMember)
                .orElseGet(() -> memberBanRepository.save(MemberBan.builder()
                        .member(member)
                        .banMember(banMember)
                        .build()));
    }

    public void deleteBan(Member member, Long banMemberId) {
        Member banMember = getBanMember(banMemberId);

        MemberBan memberBan = memberBanRepository.findByMemberAndBanMember(member, banMember)
                .orElseThrow(
                        () -> new BadRequestException(MemberStatus.MEMBER_NOT_FOUND)
                );
        memberBanRepository.delete(memberBan);
    }

    private Member getBanMember(Long banMemberId) {
        Member banMember = memberRepository.findById(banMemberId)
                .orElseThrow(
                        () -> new BadRequestException(MemberStatus.MEMBER_NOT_FOUND)
                );
        return banMember;
    }
}

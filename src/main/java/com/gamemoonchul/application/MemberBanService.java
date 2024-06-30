package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberBan;
import com.gamemoonchul.infrastructure.repository.MemberBanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberBanService {
    private final MemberBanRepository memberBanRepository;

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
}

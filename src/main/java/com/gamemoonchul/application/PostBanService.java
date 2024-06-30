package com.gamemoonchul.application;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.PostBan;
import com.gamemoonchul.infrastructure.repository.PostBanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostBanService {
    private final PostBanRepository postBanRepository;

    public List<Long> getBanPostIds(Member member) {
        List<Long> banPostIds = new ArrayList<>();
        if (member != null) {
            postBanRepository.findAllByMember(member)
                    .forEach(postBan ->
                            banPostIds.add(postBan.getBanPost()
                                    .getId()));
        }
        return banPostIds;
    }
}

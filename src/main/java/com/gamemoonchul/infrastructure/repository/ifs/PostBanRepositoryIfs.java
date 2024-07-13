package com.gamemoonchul.infrastructure.repository.ifs;

import com.gamemoonchul.domain.entity.PostBan;

import java.util.List;

public interface PostBanRepositoryIfs {
    List<PostBan> searchByMemberId(Long memberId);
}

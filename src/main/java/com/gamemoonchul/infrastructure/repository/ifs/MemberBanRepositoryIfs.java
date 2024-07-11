package com.gamemoonchul.infrastructure.repository.ifs;

import com.gamemoonchul.domain.entity.MemberBan;

import java.util.List;

public interface MemberBanRepositoryIfs {
    List<MemberBan> searchByMemberId(Long banMemberId);
}

package com.gamemoonchul.infrastructure.repository;

import com.gamemoonchul.domain.entity.PostBan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBanRepository extends JpaRepository<PostBan, Long> {
}

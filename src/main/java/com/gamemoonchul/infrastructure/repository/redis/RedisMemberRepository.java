package com.gamemoonchul.infrastructure.repository.redis;

import com.gamemoonchul.domain.entity.redis.RedisMember;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisMemberRepository extends CrudRepository<RedisMember, Long> {
    Optional<RedisMember> findRedisMemberByUniqueKey(String uniqueKey);
}

package com.gamemoonchul.infrastructure.repository.redis;

import com.gamemoonchul.domain.entity.redis.RedisPostDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisPostDetailRepository extends CrudRepository<RedisPostDetail, Long> {
    Optional<RedisPostDetail> findRedisPostDetailById(Long id);
}

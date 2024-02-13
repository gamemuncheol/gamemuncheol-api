package com.gamemoonchul.infrastructure.repository.redis;

import com.gamemoonchul.domain.redisEntity.RedisCachedGame;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RedisCachedGameRepository extends CrudRepository<RedisCachedGame, Long> {
    Optional<RedisCachedGame> findById(Long id);
    List<RedisCachedGame> findAllBySearchKeyword(String searchKeyword);
}

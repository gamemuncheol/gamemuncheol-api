package com.gamemoonchul.application.post;

import com.gamemoonchul.infrastructure.web.dto.response.PostDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostRedisService {
    private final static Long CACHE_EXPIRE_TIME = 60L * 10L; // 10분
    private final RedisTemplate<Long, PostDetailResponse> postDetailRedisTemplate;

    public void savePostDetailResponse(Long key, PostDetailResponse postDetailResponse) {
        postDetailRedisTemplate.opsForValue().set(key, postDetailResponse);
        postDetailRedisTemplate.expire(key, CACHE_EXPIRE_TIME, java.util.concurrent.TimeUnit.SECONDS);
    }

    public PostDetailResponse getPostDetailResponse(Long key) {
        return postDetailRedisTemplate.opsForValue().get(key);
    }
}

package com.gamemoonchul.application;

import com.gamemoonchul.infrastructure.web.dto.response.PostMainPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCacheService {
    private final static Long CACHE_EXPIRE_TIME = 60L * 10L; // 10분
    private final RedisTemplate<String, List<PostMainPageResponse>> postDetailRedisTemplate;

    public void savePostDetailResponse(String key, List<PostMainPageResponse> postMainPageResponses) {
        postDetailRedisTemplate.opsForValue().set(key, postMainPageResponses);
        postDetailRedisTemplate.expire(key, CACHE_EXPIRE_TIME, java.util.concurrent.TimeUnit.SECONDS);
    }

    public List<PostMainPageResponse> getPostDetailResponse(String key) {
        return postDetailRedisTemplate.opsForValue().get(key);
    }

}

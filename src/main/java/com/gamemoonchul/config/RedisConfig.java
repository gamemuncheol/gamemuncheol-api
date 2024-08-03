package com.gamemoonchul.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.dto.response.PostDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private final ObjectMapper objectMapper;

    @Bean
    RedisTemplate<String, Member> userRedisTemplate(RedisConnectionFactory connectionFactory) {
        var template = new RedisTemplate<String, Member>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, Member.class));

        return template;
    }

    @Bean
    RedisTemplate<Long, PostDetailResponse> postDetailRedisTemplate(RedisConnectionFactory connectionFactory) {
        var template = new RedisTemplate<Long, PostDetailResponse>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new GenericToStringSerializer<>(Long.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, PostDetailResponse.class));
        return template;
    }
}
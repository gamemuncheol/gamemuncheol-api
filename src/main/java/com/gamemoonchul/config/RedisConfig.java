package com.gamemoonchul.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.dto.response.PostMainPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

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
    public RedisTemplate<String, List<PostMainPageResponse>> postDetailRedisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, List<PostMainPageResponse>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Key Serializer 설정
        template.setKeySerializer(new GenericToStringSerializer<>(String.class));

        // Value Serializer 설정
        Jackson2JsonRedisSerializer<List<PostMainPageResponse>> serializer =
            new Jackson2JsonRedisSerializer<>(objectMapper.getTypeFactory().constructCollectionType(List.class, PostMainPageResponse.class));
        template.setValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}

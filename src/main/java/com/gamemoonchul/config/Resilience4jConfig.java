package com.gamemoonchul.config;

import com.gamemoonchul.common.constants.Resilience4jConstants;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean(name = Resilience4jConstants.SHORT_TERM_RIOT_API_RATE_LIMITER)
    public RateLimiter shortTermRateLimiter(RateLimiterRegistry registry) {
        return registry.rateLimiter(Resilience4jConstants.SHORT_TERM_RIOT_API_RATE_LIMITER,
            RateLimiterConfig.custom()
                .limitForPeriod(20) // 20회
                .limitRefreshPeriod(Duration.ofSeconds(1)) // 1초간
                .timeoutDuration(Duration.ofMillis(10)) // 10ms 후 즉시 예외 발생
                .build()
        );
    }

    @Bean(name = Resilience4jConstants.LONG_TERM_RIOT_API_RATE_LIMITER)
    public RateLimiter longTermRateLimiter(RateLimiterRegistry registry) {
        return registry.rateLimiter(Resilience4jConstants.LONG_TERM_RIOT_API_RATE_LIMITER,
            RateLimiterConfig.custom()
                .limitForPeriod(100) // 100회
                .limitRefreshPeriod(Duration.ofMinutes(2)) // 2분간
                .timeoutDuration(Duration.ofMillis(10)) // 10ms 후 즉시 예외 발생
                .build()
        );
    }
}

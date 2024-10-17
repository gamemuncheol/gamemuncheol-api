package com.gamemoonchul.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean(name = "shortTermCircuitBreaker")
    public CircuitBreaker shortTermCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("shortTermBreaker",
            CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                .failureRateThreshold(1) // 몇 %의 요청이 실패하면 circuit open으로 전환할지
                .slidingWindowSize(1)
                .waitDurationInOpenState(Duration.ofSeconds(1)) // open -> half open까지 기다리는 시간
                .automaticTransitionFromOpenToHalfOpenEnabled(true) // open 상태에서 자동으로 half open으로 전환
                .build()
        );
    }

    @Bean(name = "longTermCircuitBreaker")
    public CircuitBreaker longTermCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("longTermBreaker",
            CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                .failureRateThreshold(1) // 몇 %의 요청이 실패하면 circuit open으로 전환할지
                .slidingWindowSize(120)
                .waitDurationInOpenState(Duration.ofMinutes(2)) // open -> half open까지 기다리는 시간
                .automaticTransitionFromOpenToHalfOpenEnabled(true) // open 상태에서 자동으로 half open으로 전환
                .build()
        );
    }
}

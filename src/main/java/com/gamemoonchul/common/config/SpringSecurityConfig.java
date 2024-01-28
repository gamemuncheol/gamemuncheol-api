package com.gamemoonchul.common.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
  private List<String> SWAGGER = List.of("/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs", "/v3/api-docs/**");
  private List<String> EXCEPTION = List.of("/test/**");

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(it -> {
          // static Resource에 대해서는 모두 허가함
          it.requestMatchers(
                  PathRequest.toStaticResources()
                      .atCommonLocations()
              )
              .permitAll()
              .requestMatchers(SWAGGER.toArray(new String[0]))
              .permitAll()
              .requestMatchers(EXCEPTION.toArray(new String[0]))
              .permitAll()
              .anyRequest()
              .authenticated();
        });
    return httpSecurity.build();
  }
}

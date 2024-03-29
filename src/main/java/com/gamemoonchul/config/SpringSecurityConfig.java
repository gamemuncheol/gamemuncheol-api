package com.gamemoonchul.config;

import com.gamemoonchul.config.jwt.JwtAuthorizationFilter;
import com.gamemoonchul.config.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.gamemoonchul.config.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.gamemoonchul.config.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.gamemoonchul.config.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
  private List<String> SWAGGER = List.of("/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs", "/v3/api-docs/**");
  //private List<String> EXCEPTION = List.of("/test/**");
  private List<String> EXCEPTION = List.of();

  private final CustomOAuth2UserService customOauth;
  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
  private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
  private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
  private final JwtAuthorizationFilter jwtAuthorizationFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(it -> {
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
              .authenticated()
          ;
        })
        .csrf(AbstractHttpConfigurer::disable)
        .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // For H2 DB
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        // session 사용하지 않도록 설정
        .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2Login(configure ->
            configure.authorizationEndpoint(config -> config.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                .userInfoEndpoint(config -> config.userService(customOauth))
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
        );

    http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}

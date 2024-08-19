package com.samsam.travel.travelcommerce.security.config;

import com.samsam.travel.travelcommerce.entity.model.Role;
import com.samsam.travel.travelcommerce.security.auth.jwt.JwtFilter;
import com.samsam.travel.travelcommerce.security.error.handler.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 이 클래스는 Spring Boot 애플리케이션에서 보안 설정을 구성하는 책임을 담당합니다.
 * 인증, 인가, CORS, JWT 기반 인증을 포함합니다.
 *
 * @author lavin
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Value("${allowed.origins}")
    private String allowedOrigins;

    /**
     * AuthenticationManager 빈을 생성합니다.
     *
     * @param configuration AuthenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * SecurityFilterChain 빈을 생성합니다.
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .cors(withDefaults()) // CORS 활성화
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // Frame Options 비활성화
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT를 사용한 무상태 세션 정책
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests // 특정 경로에 대해 인증 및 인가 규칙을 설정
                        .requestMatchers("/swagger-ui/index.html#").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger*/**").permitAll()
                        .requestMatchers("/auth/**").permitAll() // auth로 시작하는 URI는 인증 없이 접근 가능
                        .requestMatchers("/api/tickets/view/*").permitAll() // 조회는 권한 없이 수행
                        .requestMatchers("/api/review/view/ticket").permitAll() // 조회는 권한 없이 수행
                        .requestMatchers("/api/images/**").permitAll() // 이미지 경로에 대한 접근 허용
                        .requestMatchers("/api/master/**").hasAuthority(Role.MASTER.toString()) // /api/master/**는 MASTER 권한이 있는 사용자만 접근 가능
                        .requestMatchers("/api/admin/**").hasAnyAuthority(Role.ADMIN.toString(), Role.MASTER.toString()) // /api/admin/**는 ADMIN 또는 MASTER 권한이 있는 사용자만 접근 가능
                        .requestMatchers("/api/**").authenticated() // /api/**로 시작하는 모든 URI는 인증 필요
                        .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // UsernamePasswordAuthenticationFilter 전에 jwtFilter를 추가
                .addFilterBefore(jwtExceptionFilter, JwtFilter.class); // JwtFilter 전에 jwtExceptionFilter를 추가

        return http.build();
    }

    /**
     * CORS 정책 설정을 정의합니다.
     *
     * @return CorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(","))); // 환경변수에서 읽어온 Origin URL 설정
        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "TRACE")); // CORS를 허용할 HTTP Method
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type")); // CORS를 허용할 HTTP Header
        configuration.setAllowCredentials(true); // 자격 증명 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
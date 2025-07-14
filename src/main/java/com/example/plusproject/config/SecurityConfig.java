package com.example.plusproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)  // .csrf().disable() 방식은 더 이상 사용 안함.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // session 비활성화 (Jwt 사용)
                .httpBasic(AbstractHttpConfigurer::disable) // BasicAuthenticationFilter 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // UsernamePasswordAuthenticationFilter, DefaultLoginPageGeneratingFilter 비활성화
                .addFilterBefore(jwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/signup","/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/reviews*",
                                "/api/accommodations/v3/search/keyword*", "/api/accommodations/v3/search/city*",
                                "/api/trending*").permitAll()   // 조회 로직 - 로그인 불필요
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

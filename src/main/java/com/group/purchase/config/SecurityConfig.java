package com.group.purchase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//이 클래스가 애플리케이션의 설정 정보를 담고 있는 클래스임을 알림
@EnableWebSecurity
//Spring Security 기능을 활성화
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //BCrypt 방식을 사용한 비밀번호 암호화
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                //CSRF(사이트 간 요청 위조) 보호 기능을 끔 -> 토큰을 사용으로 CSRF 공격 위험이 적음
                //AbstractHttpConfigurer::disable: csrf -> csrf.disable()과 완전히 같은 역할
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //session.sessionCreationPolicy: 전달받은 세션 관리 객체에서 세션 생성 정책 지정
                //.STATELESS: 앞으로 인증을 처리할 때 서버 쪽에 세션을 아예 생성X, 사용X -> 무상태
                .authorizeHttpRequests(auth -> auth
                        //어떤 주소(URL)에 누가 접근할 수 있는지 권한(인가) 설정
                        .requestMatchers("/api/auth/**", "/api/boards/**").permitAll()
                        //requestMatchers: 권한을 설정할 특정 경로들을 지정
                        //permitAll(): 앞서 지정한 경로들에 대해서는 인증(로그인) 절차 없이 누구에게나 접근
                        .anyRequest().authenticated()
                        //authenticated(): requestMatchers로 허용해 준 경로들을 제외한 나머지 모든 요청은 반드시 로그인(차단)
                );

        return http.build();
    }
}
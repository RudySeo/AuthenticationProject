package com.example.authenticationproject.config

import com.example.authenticationproject.utill.jwt.JwtAuthenticationFilter
import com.example.authenticationproject.utill.jwt.JwtTokenProvider
import com.example.authenticationproject.utill.security.filter.JwtLoginFilter
import com.example.authenticationproject.utill.security.handler.JwtAuthenticationFailureHandler
import com.example.authenticationproject.utill.security.handler.JwtAuthenticationSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig
    (
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: UserDetailsService
) {

    /**
     * 인증 매니저 등록
     */

    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager = authenticationConfiguration.authenticationManager

    /**
     * 비밀번호 암호화 방식 설정 (BCrypt 사용)
     */

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()


    @Bean
    fun filterChain(http: HttpSecurity, authManager: AuthenticationManager): SecurityFilterChain {
        // 🔐 로그인 요청을 처리하는 커스텀 필터
        val loginFilter = JwtLoginFilter(
            authenticationManager = authManager,
            successHandler = JwtAuthenticationSuccessHandler(jwtTokenProvider),
            failureHandler = JwtAuthenticationFailureHandler()
        )
        loginFilter.setAuthenticationManager(authManager)

        return http
            // CSRF 비활성화 (JWT 기반이므로 필요 없음)
            .csrf { it.disable() }
            // 세션 미사용 설정 → STATELESS (JWT 방식이기 때문에)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

            // 🔓 인가 설정
            .authorizeHttpRequests {
                it.requestMatchers("/member/**").permitAll()
                    .anyRequest().authenticated()
            }
            // 🔐 커스텀 로그인 필터 등록 (/member/login 엔드포인트에서 로그인 처리)
            .addFilter(loginFilter)
            
            // 🛡️ JWT 인증 필터를 UsernamePasswordAuthenticationFilter 이전에 등록
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider, userDetailsService),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }
}
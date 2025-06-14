package com.example.authenticationproject.config

import com.example.authenticationproject.utill.jwt.JwtAuthenticationFilter
import com.example.authenticationproject.utill.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configurable
@EnableWebSecurity
class SecurityConfig
    (
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: UserDetailsService
) {

    /**
     * UserDetailsService와 PasswordEncoder를 명시적으로 설정해 인증 로직이 UserDetails 기반으로 동작하도록 구성합니다
     * 로그인 처리 시 AuthenticationProvider를 통해 인증을 위임하는 데 사용됩니다
     */

    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val authBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
        return authBuilder.build()
    }

    /**
     * 비밀번호 암호화를 위한 PasswordEncoder 입니다
     * BCrypt 해싱 함수를 사용하여 사용자 비밀번호를 안전하게 저장 및 비교합니다
     */

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers("/member/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider, userDetailsService),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }
}
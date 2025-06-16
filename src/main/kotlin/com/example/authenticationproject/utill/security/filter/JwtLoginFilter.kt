package com.example.authenticationproject.utill.security.filter

import com.example.authenticationproject.member.MemberDto
import com.example.authenticationproject.utill.security.handler.JwtAuthenticationFailureHandler
import com.example.authenticationproject.utill.security.handler.JwtAuthenticationSuccessHandler
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtLoginFilter(
    private val authenticationManager: AuthenticationManager,
    private val successHandler: JwtAuthenticationSuccessHandler,
    private val failureHandler: JwtAuthenticationFailureHandler
) : UsernamePasswordAuthenticationFilter() {


    init {
        setFilterProcessesUrl("/member/login") // 로그인 처리 URL 지정
        super.setAuthenticationManager(authenticationManager)
        this.setAuthenticationSuccessHandler(successHandler)
        this.setAuthenticationFailureHandler(failureHandler)
    }

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val mapper = ObjectMapper()
        val loginRequest = mapper.readValue(request.inputStream, MemberDto.LoginRequest::class.java)
        val authToken =
            UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        return authenticationManager.authenticate(authToken)
    }
}
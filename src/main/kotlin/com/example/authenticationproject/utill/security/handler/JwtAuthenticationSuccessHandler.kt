package com.example.authenticationproject.utill.security.handler

import com.example.authenticationproject.member.MemberDto
import com.example.authenticationproject.utill.jwt.JwtTokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationSuccessHandler(private val jwtTokenProvider: JwtTokenProvider) :
    AuthenticationSuccessHandler {


    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val accessToken = jwtTokenProvider.generateAccessToken(authentication)
        val refreshToken = jwtTokenProvider.generateRefreshToken(authentication)

        val loginResponse = MemberDto.LoginResponse(accessToken, refreshToken)

        response.contentType = "application/json"
        response.characterEncoding = "utf-8"
        ObjectMapper().writeValue(response.writer, loginResponse)
    }

}

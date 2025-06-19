package com.example.authenticationproject.utill.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class JwtAuthenticationFailureHandler : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        log.warn { "🔒 로그인 실패 - 예외 타입: ${exception.javaClass.simpleName}, 메시지: ${exception.message}" }

        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.characterEncoding = "utf-8"


        val errorResponse = mapOf(
            "error" to "Unauthorized",
            "message" to "아이디 또는 비밀번호가 틀렸습니다.",
        )

        ObjectMapper().writeValue(response.writer, errorResponse)
    }

}
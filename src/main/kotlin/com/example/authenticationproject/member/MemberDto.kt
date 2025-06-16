package com.example.authenticationproject.member

class MemberDto {

    data class SignupRequest(
        val username: String,
        val password: String,
        val email: String
    )

    data class SignupResponse(
        val id: Long,
        val username: String,
        val email: String,
        val message: String = "회원가입이 완료되었습니다."
    )

    data class LoginRequest(
        val email: String = "",
        val password: String = ""
    )

    data class LoginResponse(
        val accessToken: String,
        val refreshToken: String,
    )

    data class MemberResponse(
        val username: String,
        val email: String
    )
}
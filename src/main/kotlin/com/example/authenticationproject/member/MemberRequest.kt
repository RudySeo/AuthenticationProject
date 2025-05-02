package com.example.authenticationproject.member

data class MemberRequest(
    val username: String,
    val password: String,
    val email: String
)

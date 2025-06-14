package com.example.authenticationproject.member

import com.example.authenticationproject.utill.jwt.JwtTokenProvider
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) {

    fun signup(request: MemberDto.SignupRequest) {
        val user = Member(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            email = request.email,
            role = Role.USER,
        )
        memberRepository.save(user)
    }

    fun login(request: MemberDto.LoginRequest): MemberDto.LoginResponse {
        // 1. 인증 토큰 생성
        val authToken = UsernamePasswordAuthenticationToken(request.email, request.password)

        // 2. 인증 수행
        val authentication: Authentication = authenticationManager.authenticate(authToken)

        // 3. 토큰 발급
        val accessToken = jwtTokenProvider.generateAccessToken(authentication)
        val refreshToken = jwtTokenProvider.generateRefreshToken(authentication)

        return MemberDto.LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun getMemberById(id: Long): MemberDto.MemberResponse {
        val member = memberRepository.findById(id).orElseThrow { NotFoundException() }

        return MemberDto.MemberResponse(member.username, member.email)
    }
}

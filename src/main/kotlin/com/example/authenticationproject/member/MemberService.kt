package com.example.authenticationproject.member

import com.example.authenticationproject.utill.jwt.JwtTokenProvider
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.authentication.AuthenticationManager
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
    
    fun getMemberById(id: Long): MemberDto.MemberResponse {
        val member = memberRepository.findById(id).orElseThrow { NotFoundException() }

        return MemberDto.MemberResponse(member.username, member.email)
    }
}

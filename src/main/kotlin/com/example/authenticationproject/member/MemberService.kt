package com.example.authenticationproject.member

import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    fun signup(request: MemberDto.SignupRequest) {
        val user = Member(
            username = request.username,
            password = request.password,
            email = request.email
        )
        memberRepository.save(user)
    }

    fun login(request: MemberDto.LoginRequest): MemberDto.LoginResponse {

        val findMember = memberRepository.findByEmail(request.email) ?: throw IllegalStateException(
            "User not found"
        )

        return MemberDto.LoginResponse(email = findMember.get().email)


    }
}

package com.example.authenticationproject.member

import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    fun signup(request: MemberRequest) {
        val user = Member(
            username = request.username,
            password = request.password,
            email = request.email
        )
        memberRepository.save(user)
    }
}

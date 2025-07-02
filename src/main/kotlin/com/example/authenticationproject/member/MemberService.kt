package com.example.authenticationproject.member

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
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
        val member = memberRepository.findOneById(id)
            ?: throw UsernameNotFoundException("ㅇ")

        return MemberDto.MemberResponse(member.username, member.email)
    }
}

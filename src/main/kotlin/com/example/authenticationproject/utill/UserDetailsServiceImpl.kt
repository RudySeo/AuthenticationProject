package com.example.authenticationproject.utill

import com.example.authenticationproject.member.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val memberRepository: MemberRepository) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        // 1. 이메일로 유저 조회
        val user = memberRepository.findByEmail(email).orElseThrow {
            UsernameNotFoundException("해당 유저를 찾을 수 없습니다.")
        }
        
        // 2. 스프링에서 사용할 수 있는 UserDetails 객체로 반환
        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))
        )
    }
}
package com.example.authenticationproject.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {

    fun findByEmail(email: String): Member?

    fun findOneById(id: Long): Member? // 커스텀 메서드
}

package com.example.authenticationproject.member

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberController(private val memberService: MemberService) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: MemberRequest): ResponseEntity<String> {
        memberService.signup(request)
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.")
    }

}
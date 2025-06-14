package com.example.authenticationproject.utill.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-exp}") private val accessTokenExp: Long,
    @Value("\${jwt.refresh-exp}") private val refreshTokenExp: Long
) {
    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateAccessToken(authentication: Authentication): String {
        val now = Date()
        val expiryDate = Date(now.time + accessTokenExp)

        return Jwts.builder()
            .setSubject(authentication.name)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun generateRefreshToken(authentication: Authentication): String {
        val now = Date()
        val expiryDate = Date(now.time + refreshTokenExp)

        return Jwts.builder()
            .setSubject(authentication.name)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun getUsernameFromToken(token: String): String =
        Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token).body.subject

    fun validateToken(token: String): Boolean = try {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
        true
    } catch (ex: Exception) {
        false
    }
}

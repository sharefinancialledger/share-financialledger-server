package com.sharefinancialledger.auth.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTUtil(
        @Value("share-financialledger.auth.jwt.secret")
        private val secret: String
) {


    fun extractUsername(token: String): String {
        return extractClaim(token) { it.subject }
    }

    fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    fun validateToken(token: String, user: UserDetails): Boolean {
        return extractUsername(token) == user.username && isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(username: String): String {
        // TODO add name
        return createToken(mutableMapOf(), username)
    }

    private fun createToken(claims: MutableMap<String, Any>, username: String): String {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_MILLISECOND))
                .signWith(SignatureAlgorithm.HS256, secret).compact()

    }

    companion object {
        const val EXPIRATION_MILLISECOND = 1000 * 60 * 60 * 5 // 5 hour
    }
}
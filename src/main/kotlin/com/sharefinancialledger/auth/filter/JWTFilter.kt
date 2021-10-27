package com.sharefinancialledger.auth.filter

import com.sharefinancialledger.auth.service.JWTUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class JWTFilter(
        private val jwtUtil: JWTUtil,
        private val userDetailService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")
        val token = authorizationHeader
                ?.takeIf { authorizationHeader.startsWith("Bearer ") }
                ?.let { authorizationHeader.substring(7) }

        token?.let { jwtUtil.extractUsername(token) }
                ?.takeIf { SecurityContextHolder.getContext().authentication == null }
                ?.let { username -> userDetailService.loadUserByUsername(username) }
                ?.takeIf { userDetails -> jwtUtil.validateToken(token, userDetails) }
                ?.let { userDetails -> UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities) }
                ?.apply { details = WebAuthenticationDetailsSource().buildDetails(request) }
                ?.also { SecurityContextHolder.getContext().authentication = it }

        filterChain.doFilter(request, response)
    }
}
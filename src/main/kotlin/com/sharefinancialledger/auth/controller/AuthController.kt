package com.sharefinancialledger.auth.controller

import com.sharefinancialledger.auth.dto.AuthenticationRequest
import com.sharefinancialledger.auth.dto.AuthenticationResponse
import com.sharefinancialledger.auth.service.JWTUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthController(
        private val authenticationManager: AuthenticationManager,
        private val jwtUtil: JWTUtil
) {

    @PostMapping("/v1/authentication")
    fun authenticate(@RequestBody request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
        return AuthenticationResponse(jwtUtil.generateToken(request.email))
    }
}
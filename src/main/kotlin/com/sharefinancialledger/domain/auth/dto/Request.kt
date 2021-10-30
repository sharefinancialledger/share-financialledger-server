package com.sharefinancialledger.domain.auth.dto

data class AuthenticationRequest(
        val email: String,
        val password: String
)
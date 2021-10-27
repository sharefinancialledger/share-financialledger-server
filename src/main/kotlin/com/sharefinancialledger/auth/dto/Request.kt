package com.sharefinancialledger.auth.dto

data class AuthenticationRequest(
        val email: String,
        val password: String
)
package com.sharefinancialledger.domain.user.controller.dto

data class CreateUserRequest(
        val email: String,
        val password: String,
        val name: String
)

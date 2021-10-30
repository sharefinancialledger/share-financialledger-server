package com.sharefinancialledger.domain.user.service

import com.sharefinancialledger.global.exception.BadRequestException
import com.sharefinancialledger.domain.user.controller.dto.CreateUserRequest
import com.sharefinancialledger.domain.user.entity.User
import com.sharefinancialledger.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
        private val repository: UserRepository,
        private val passwordEncoder: PasswordEncoder
){

    fun create(request: CreateUserRequest): Int {
        if (repository.findFirstByEmail(request.email).isPresent) throw BadRequestException("중복된 이메일입니다.")
        return User(email = request.email, password = passwordEncoder.encode(request.password), name = request.name)
                .let { repository.save(it) }
                .id!!

    }
}
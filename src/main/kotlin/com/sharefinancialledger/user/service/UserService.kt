package com.sharefinancialledger.user.service

import com.sharefinancialledger.exception.BadRequestException
import com.sharefinancialledger.user.controller.dto.CreateUserRequest
import com.sharefinancialledger.user.entity.User
import com.sharefinancialledger.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
        private val repository: UserRepository
) {

    fun create(request: CreateUserRequest): Int {
        if (repository.findFirstByEmail(request.email).isPresent) throw BadRequestException("중복된 이메일입니다.")
        return User(email = request.email, password = request.password, name = request.name)
                .let { repository.save(it) }
                .id!!

    }
}
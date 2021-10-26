package com.sharefinancialledger.user.controller

import com.sharefinancialledger.user.controller.dto.CreateUserRequest
import com.sharefinancialledger.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController(
        private val service: UserService
) {

    @PostMapping("/v1/user")
    fun create(@RequestBody request: CreateUserRequest) {
        service.create(request)
    }
}
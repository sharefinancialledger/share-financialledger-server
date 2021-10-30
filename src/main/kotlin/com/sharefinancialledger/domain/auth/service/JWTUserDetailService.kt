package com.sharefinancialledger.domain.auth.service

import com.sharefinancialledger.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JWTUserDetailService(
        private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findFirstByEmail(email).orElseThrow { UsernameNotFoundException("잘못된 이메일입니다.") }
        return User(user.email, user.password, emptyList())
    }

}
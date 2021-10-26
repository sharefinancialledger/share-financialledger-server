package com.sharefinancialledger.user.repository

import com.sharefinancialledger.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface UserRepository : JpaRepository<User, Int> {

    @Transactional(readOnly = true)
    fun findFirstByEmail(email: String): Optional<User>
}
package com.sharefinancialledger.domain.category.repository

import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.global.entity.type.TransactionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface CategoryRepository : JpaRepository<Category, Int> {

    @Transactional(readOnly = true)
    fun existsByUserIdAndTitle(userId: Int, title: String): Boolean

    @Transactional(readOnly = true)
    fun findByIdAndUserId(id: Int, userId: Int): Category?

    @Transactional(readOnly = true)
    fun findByUserIdAndTransactionType(userId: Int, transactionType: TransactionType): List<Category>

}
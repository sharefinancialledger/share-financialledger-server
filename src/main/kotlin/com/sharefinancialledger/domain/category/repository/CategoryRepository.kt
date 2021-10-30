package com.sharefinancialledger.domain.category.repository

import com.sharefinancialledger.domain.category.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface CategoryRepository : JpaRepository<Category, Int> {

    @Transactional(readOnly = true)
    fun existsByUserIdAndTitle(userId: Int, title: String): Boolean
}

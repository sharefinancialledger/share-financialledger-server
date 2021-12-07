package com.sharefinancialledger.domain.subcategory.repository

import com.sharefinancialledger.domain.subcategory.entity.SubCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface SubCategoryRepository : JpaRepository<SubCategory, Int>{

    @Transactional(readOnly = true)
    fun existsByUserIdAndCategoryId(userId: Int, categoryId: Int): Boolean?

    @Transactional(readOnly = true)
    fun existsByUserIdAndCategoryIdAndTitle(userId: Int, categoryId: Int, title: String): Boolean?

    @Transactional(readOnly = true)
    fun findAllByUserId(userId: Int): List<SubCategory>
}
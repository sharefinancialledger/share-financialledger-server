package com.sharefinancialledger.domain.category.service

import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.controller.dto.UpdateCategoryRequest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import javassist.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(private val repository: CategoryRepository) {
    fun create(userId: Int, request: CreateCategoryRequest): Category {
        return repository.save(Category(userId = userId, title = request.title, transactionType = request.transactionType))
    }

    @Transactional
    fun update(categoryId: Int, userId: Int, request: UpdateCategoryRequest) {
        val category = repository.findByIdAndUserId(categoryId, userId) ?: throw NotFoundException("잘못된 카테고리 ID입니다.")
        category.title = request.title
    }
}

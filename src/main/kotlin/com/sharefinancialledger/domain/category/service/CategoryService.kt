package com.sharefinancialledger.domain.category.service

import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val repository: CategoryRepository) {
    fun create(userId: Int, request: CreateCategoryRequest): Category {
        return repository.save(Category(userId = userId, title = request.title, transactionType = request.transactionType))
    }
}

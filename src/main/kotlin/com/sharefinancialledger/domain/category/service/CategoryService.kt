package com.sharefinancialledger.domain.category.service

import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.global.entity.type.TransactionType
import com.sharefinancialledger.global.exception.BadRequestException
import org.springframework.stereotype.Service

@Service
class CategoryService(private val repository: CategoryRepository) {
    fun create(userId: Int, request: CreateCategoryRequest): Category {
        if (repository.existsByUserIdAndTitle(userId, request.title)) throw BadRequestException("중복된 카테고리 명입니다.")
        return repository.save(Category(userId = userId, title = request.title, transactionType = request.transactionType))
    }

    fun find(userId: Int, transactionType: TransactionType): List<Category> {
        return repository.findByUserIdAndTransactionType(userId, transactionType)
    }
}

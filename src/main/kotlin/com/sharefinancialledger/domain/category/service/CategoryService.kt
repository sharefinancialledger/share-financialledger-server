package com.sharefinancialledger.domain.category.service

import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.controller.dto.UpdateCategoryRequest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.global.entity.type.TransactionType
import javassist.NotFoundException
import com.sharefinancialledger.global.exception.AuthorizationException
import com.sharefinancialledger.global.exception.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(private val repository: CategoryRepository) {
    fun create(userId: Int, request: CreateCategoryRequest): Category {
        if (repository.existsByUserIdAndTitle(userId, request.title)) throw BadRequestException("중복된 카테고리 명입니다.")
        return repository.save(Category(userId = userId, title = request.title, transactionType = request.transactionType))
    }

    @Transactional
    fun update(categoryId: Int, userId: Int, request: UpdateCategoryRequest) {
        val category = repository.findByIdAndUserId(categoryId, userId) ?: throw NotFoundException("잘못된 카테고리 ID입니다.")
        category.title = request.title
    }

    fun find(userId: Int, transactionType: TransactionType): List<Category> {
        return repository.findByUserIdAndTransactionType(userId, transactionType)
    }

    @Transactional
    fun delete(categoryId: Int, userId: Int) {
        val category = findCategoryOrRaiseIfNotExist(categoryId, userId)
        category.delete()
    }

    private fun findCategoryOrRaiseIfNotExist(categoryId: Int, userId: Int): Category {
        val category = repository.findById(categoryId).orElseThrow { throw BadRequestException("카테고리가 존재하지 않습니다") }
        if (category.isOwn(userId).not()) throw AuthorizationException("접근할 수 없는 카테고리입니다.")
        return category
    }
}

package com.sharefinancialledger.domain.category.service

import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.controller.dto.UpdateCategoryRequest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.global.entity.type.TransactionType
import com.sharefinancialledger.global.exception.BadRequestException
import javassist.NotFoundException
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
        val category = findOwn(categoryId, userId)
        category.title = request.title
    }

    @Transactional
    fun delete(categoryId: Int, userId: Int) {
        val category = findOwn(categoryId, userId)
        category.delete()
    }

    fun find(userId: Int, transactionType: TransactionType): List<Category> {
        return repository.findByUserIdAndTransactionType(userId, transactionType)
    }

    fun findOwn(categoryId: Int, userId: Int): Category {
        return findOrRaiseIfNotExist(categoryId).also { it.raiseIfIsNotOwn(userId) }
    }

    private fun findOrRaiseIfNotExist(categoryId: Int): Category {
        return repository.findById(categoryId).orElseThrow { throw NotFoundException("카테고리가 존재하지 않습니다") }
    }
}

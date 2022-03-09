package com.sharefinancialledger.domain.transactionlog.service

import com.sharefinancialledger.domain.category.service.CategoryService
import com.sharefinancialledger.domain.subcategory.service.SubCategoryService
import com.sharefinancialledger.domain.transactionlog.controller.dto.CreateTransactionLogRequest
import com.sharefinancialledger.domain.transactionlog.controller.dto.FindTransactionLogRequest
import com.sharefinancialledger.domain.transactionlog.entity.TransactionLog
import com.sharefinancialledger.domain.transactionlog.repository.TransactionLogRepository
import org.springframework.stereotype.Service

@Service
class TransactionLogService(
    private val repository: TransactionLogRepository,
    private val categoryService: CategoryService,
    private val subCategoryService: SubCategoryService
) {

    fun create(userId: Int, request: CreateTransactionLogRequest): TransactionLog {
        val category = categoryService.findOwn(request.categoryId, userId)
        val subCategory = request.subCategoryId?.let { subCategoryService.findOwn(request.subCategoryId, userId) }
        return TransactionLog(
            userId = userId,
            date = request.date,
            name = request.name,
            amount = request.amount
        ).also {
            it.category = category
            it.subCategory = subCategory
        }.let { repository.save(it) }
    }

    fun findAll(userId: Int, request: FindTransactionLogRequest): List<TransactionLog> {
        return repository.findAllByUserIdAndDateBetweenOrderByDateDesc(userId, request.startDate, request.endDate)
    }
}
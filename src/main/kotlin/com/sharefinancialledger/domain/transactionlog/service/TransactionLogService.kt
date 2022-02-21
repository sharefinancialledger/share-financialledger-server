package com.sharefinancialledger.domain.transactionlog.service

import com.sharefinancialledger.domain.category.service.CategoryService
import com.sharefinancialledger.domain.transactionlog.controller.dto.CreateTransactionLogRequest
import com.sharefinancialledger.domain.transactionlog.entity.TransactionLog
import com.sharefinancialledger.domain.transactionlog.repository.TransactionLogRepository
import org.springframework.stereotype.Service

@Service
class TransactionLogService(
        private val repository: TransactionLogRepository,
        private val categoryService: CategoryService
) {

    fun create(userId: Int, request: CreateTransactionLogRequest) {
        val category = categoryService.findOwn(request.categoryId, userId)
        return TransactionLog(
                userId = userId,
                date = request.date,
                name = request.name,
                amount = request.amount
        )
                .apply { this.category = category }
                .let { repository.save(it) }
    }
}
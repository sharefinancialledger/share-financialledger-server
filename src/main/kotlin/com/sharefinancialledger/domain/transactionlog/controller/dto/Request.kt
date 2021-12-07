package com.sharefinancialledger.domain.transactionlog.controller.dto

import java.time.LocalDate

data class CreateTransactionLogRequest(
        val date: LocalDate,
        val name: String,
        val amount: Int,
        val categoryId: Int,
        val subCategoryId: Int? = null
)
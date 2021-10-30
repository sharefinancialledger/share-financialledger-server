package com.sharefinancialledger.domain.category.controller.dto

import com.sharefinancialledger.global.entity.type.TransactionType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateCategoryRequest(
        @get:NotBlank
        @get:Size(message = "카테고리명은 최대 30글자까지 가능합니다.", max = 30)
        val title: String,
        val transactionType: TransactionType
)


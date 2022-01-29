package com.sharefinancialledger.domain.category.controller.dto

import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.global.entity.type.TransactionType


data class FindCategoriesResponse(
        val categories: List<CategoryResponse>
) {
    companion object {
        fun listOf(categories: List<Category>): FindCategoriesResponse {
            return FindCategoriesResponse(categories.map { CategoryResponse.of(it) })
        }
    }
}

data class FindCategoryResponse(
        val category: CategoryResponse
)

data class CategoryResponse(
        val id: Int,
        val title: String,
        val transactionType: TransactionType
) {
    companion object {
        fun of(category: Category) = CategoryResponse(
                id = category.id!!,
                title = category.title,
                transactionType = category.transactionType
        )
    }
}
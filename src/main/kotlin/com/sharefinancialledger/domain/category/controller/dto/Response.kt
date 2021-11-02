package com.sharefinancialledger.domain.category.controller.dto

import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.global.entity.type.TransactionType


data class FindCategoriesResponse(
        val categories: List<CategoryElement>
) {
    data class CategoryElement(
            val id: Int,
            val title: String,
            val transactionType: TransactionType
    ) {
        companion object {
            fun of(category: Category) = CategoryElement(
                    id = category.id!!,
                    title = category.title,
                    transactionType = category.transactionType
            )
        }
    }

    companion object {
        fun listOf(categories: List<Category>): FindCategoriesResponse {
            return FindCategoriesResponse(categories.map { CategoryElement.of(it) })
        }
    }
}
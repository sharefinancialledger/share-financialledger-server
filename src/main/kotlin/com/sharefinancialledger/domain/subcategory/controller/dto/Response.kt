package com.sharefinancialledger.domain.subcategory.controller.dto

import com.sharefinancialledger.domain.subcategory.entity.SubCategory


data class FindSubCategoriesResponse(
        val subCategories: List<SubCategoryResponse>
) {
    companion object {
        fun listOf(categories: List<SubCategory>): FindSubCategoriesResponse {
            return FindSubCategoriesResponse(categories.map { SubCategoryResponse.from(it) })
        }
    }
}

data class FindSubCategoryResponse(
        val subCategory: SubCategoryResponse
)

data class SubCategoryResponse(
        val id: Int,
        val categoryId: Int,
        val title: String,
) {
    companion object {
        fun from(subCategory: SubCategory) = SubCategoryResponse(
                id = subCategory.id!!,
                categoryId = subCategory.categoryId!!,
                title = subCategory.title,
        )
    }
}
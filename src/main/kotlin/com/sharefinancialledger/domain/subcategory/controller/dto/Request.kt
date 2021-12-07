package com.sharefinancialledger.domain.subcategory.controller.dto

data class CreateSubCategoryRequest(
        val title: String,
        val category: Int
)

data class UpdateInfoRequest(
        val title: String
)
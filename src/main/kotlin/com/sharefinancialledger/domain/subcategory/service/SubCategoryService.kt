package com.sharefinancialledger.domain.subcategory.service

import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.subcategory.controller.dto.CreateSubCategoryRequest
import com.sharefinancialledger.domain.subcategory.entity.SubCategory
import com.sharefinancialledger.domain.subcategory.repository.SubCategoryRepository
import org.springframework.stereotype.Service

@Service
class SubCategoryService(private val repository: SubCategoryRepository) {
    fun create(userId: Int, request: CreateSubCategoryRequest): SubCategory {
        return repository.save(SubCategory(userId = userId, title = request.title, categoryId = request.category))
    }
}

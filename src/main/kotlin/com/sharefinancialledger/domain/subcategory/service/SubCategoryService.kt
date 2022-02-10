package com.sharefinancialledger.domain.subcategory.service

import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.subcategory.controller.dto.CreateSubCategoryRequest
import com.sharefinancialledger.domain.subcategory.controller.dto.UpdateInfoRequest
import com.sharefinancialledger.domain.subcategory.entity.SubCategory
import com.sharefinancialledger.domain.subcategory.repository.SubCategoryRepository
import javassist.NotFoundException
import org.springframework.stereotype.Service

@Service
class SubCategoryService(private val repository: SubCategoryRepository) {
    fun create(userId: Int, request: CreateSubCategoryRequest): SubCategory {
        return repository.save(SubCategory(userId = userId, title = request.title, categoryId = request.category))

    }

    fun update(userId: Int, subCategoryId: Int, request: UpdateInfoRequest): SubCategory {
        val subCategory = findOwn(subCategoryId, userId)
        subCategory.title = request.title
        return subCategory
    }

    fun delete(userId: Int, subCategoryId: Int): Unit {
        val subCategory = findOwn(subCategoryId, userId)
        subCategory.delete()
    }

    fun findOwn(subCategoryId: Int, userId: Int): SubCategory {
        return findOrRaiseIfNotExist(subCategoryId).also { it.raiseIfIsNotOwn(userId) }
    }

    private fun findOrRaiseIfNotExist(subCategoryId: Int): SubCategory {
        return repository.findById(subCategoryId).orElseThrow { throw NotFoundException("서브카테고리가 존재하지 않습니다") }
    }
}

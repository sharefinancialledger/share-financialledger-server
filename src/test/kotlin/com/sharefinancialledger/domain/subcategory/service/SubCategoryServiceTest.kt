package com.sharefinancialledger.domain.subcategory.service

import com.sharefinancialledger.common.argThat
import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.domain.subcategory.controller.dto.CreateSubCategoryRequest
import com.sharefinancialledger.domain.subcategory.controller.dto.UpdateInfoRequest
import com.sharefinancialledger.domain.subcategory.entity.SubCategory
import com.sharefinancialledger.domain.subcategory.repository.SubCategoryRepository
import com.sharefinancialledger.global.entity.type.TransactionType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import kotlin.collections.ArrayList

class SubCategoryServiceTest {

    private val repository: SubCategoryRepository = Mockito.mock(SubCategoryRepository::class.java)
    private val service = SubCategoryService(repository)


    private val userId = 100

    @Test
    fun `create sub category`() {
        val request = CreateSubCategoryRequest("카테고리", 10)
        val subCategory = SubCategory(userId = userId, title = request.title, categoryId = request.category)
        given(repository.save(argThat { it.userId == userId && it.title == request.title && it.categoryId == request.category }))
                .willReturn(subCategory)

        val resultCategory = service.create(userId, request)
        assertThat(resultCategory).isEqualTo(subCategory)
    }

//    @Test
//    fun `return subcategory by userId and subCategoryId`() {
//        var userId = 1
//        val subCategories = ArrayList<SubCategory>()
//        subCategories.add(SubCategory(id=0,userId = userId, title = "testOne", categoryId = 1))
//        subCategories.add(SubCategory(id=1, userId = userId, title = "testTwo", categoryId = 1))
//
//        given(repository.findById(0))
//                .willReturn(Optional.of(subCategories[0]))
//        given(repository.findById(1))
//                .willReturn(Optional.of(subCategories[1]))
//
//        val resultSubCategory = service.findOwn(0, userId)
//        assertThat(resultSubCategory).isEqualTo(subCategories[0])
//    }

    @Test
    fun `update title in subCategoryEntity`() {
        val subCategoryId = 1
        val targetSubCategoryTitle = "target subcategory"
        val beforeUpdateSubCateogry = SubCategory(id = 1, userId = 1, title = "source subcategory", categoryId = 1)
        given(repository.findById(subCategoryId))
                .willReturn(Optional.of(beforeUpdateSubCateogry))
        val afterUpdateSubCategory = service.update(1, subCategoryId, UpdateInfoRequest(targetSubCategoryTitle))

        beforeUpdateSubCateogry.title = targetSubCategoryTitle
        assertThat(afterUpdateSubCategory).isEqualTo(beforeUpdateSubCateogry)
    }

    @Test
    fun `delete subCategory`() {
        var userId = 1
        val subCategory = SubCategory(id=0,userId = userId, title = "testOne", categoryId = 1)
        given(repository.findById(0))
                .willReturn(Optional.of(subCategory))
        service.delete(1, 0)
        assertThat(subCategory.deletedAt).isNotNull()

    }
}

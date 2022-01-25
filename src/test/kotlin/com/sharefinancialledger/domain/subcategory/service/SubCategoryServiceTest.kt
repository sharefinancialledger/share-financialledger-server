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

class SubCategoryServiceTest {

    private val repository: SubCategoryRepository = Mockito.mock(SubCategoryRepository::class.java)
    private val service = SubCategoryService(repository)


    private val userId = 100

    @Test
    fun `서브카테고리를 생성한다`() {
        val request = CreateSubCategoryRequest("카테고리", 10)
        val subCategory = SubCategory(userId = userId, title = request.title, categoryId = request.category)
        given(repository.save(argThat { it.userId == userId && it.title == request.title && it.categoryId == request.category }))
                .willReturn(subCategory)

        val resultCategory = service.create(userId, request)
        assertThat(resultCategory).isEqualTo(subCategory)
    }

    @Test
    fun `return subcategories by userId`() {
        var userId = 1
        val subCategories = ArrayList<SubCategory>()
        subCategories.add(SubCategory(userId = userId, title = "testOne", categoryId = 1))
        subCategories.add(SubCategory(userId = userId, title = "testTwo", categoryId = 1))

        given(repository.findAllByUserId(userId))
                .willReturn(subCategories)

        val resultCategory = service.get(userId)
        assertThat(resultCategory[0]).isEqualTo(subCategories[0])
        assertThat(resultCategory[1]).isEqualTo(subCategories[1])

    }

    @Test
    fun `update title in subCategoryEntity`() {
        val subCategoryId = 1
        val targetSubCategoryTitle = "target subcategory"
        val beforeUpdateSubCateogry = SubCategory(id = 1, userId = 1, title = "source subcategory", categoryId = 1)
        given(repository.getById(subCategoryId))
                .willReturn(beforeUpdateSubCateogry)
        val afterUpdateSubCategory = service.update(subCategoryId, UpdateInfoRequest(targetSubCategoryTitle))

        beforeUpdateSubCateogry.title = targetSubCategoryTitle
        assertThat(afterUpdateSubCategory).isEqualTo(beforeUpdateSubCateogry)
    }
}

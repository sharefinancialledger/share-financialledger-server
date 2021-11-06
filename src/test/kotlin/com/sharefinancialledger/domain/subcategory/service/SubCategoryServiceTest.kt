package com.sharefinancialledger.domain.subcategory.service

import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.domain.subcategory.controller.dto.CreateSubCategoryRequest
import com.sharefinancialledger.domain.subcategory.entity.SubCategory
import com.sharefinancialledger.domain.subcategory.repository.SubCategoryRepository
import com.sharefinancialledger.global.entity.type.TransactionType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.argThat
import org.mockito.BDDMockito.given
import org.mockito.Mockito

class SubCategoryServiceTest {

    private val repository: SubCategoryRepository = Mockito.mock(SubCategoryRepository::class.java)
    private val service = SubCategoryService(repository)

    @Nested
    inner class CreateSubCategory {

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
    }
}

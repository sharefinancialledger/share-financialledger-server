package com.sharefinancialledger.domain.category.service

import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.global.entity.type.TransactionType
import com.sharefinancialledger.global.exception.BadRequestException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.argThat
import org.mockito.BDDMockito.given
import org.mockito.Mockito

class CategoryServiceTest {

    private val repository: CategoryRepository = Mockito.mock(CategoryRepository::class.java)
    private val service = CategoryService(repository)

    @Nested
    inner class CreateCategory {

        private val userId = 100

        private val request = CreateCategoryRequest("카테고리", TransactionType.INCOME)

        @Test
        fun `카테고리를 생성한다`() {
            given(repository.existsByUserIdAndTitle(userId, request.title)).willReturn(false)
            val category = Category(userId = userId, title = request.title, transactionType = request.transactionType)
            given(repository.save(argThat { it.userId == userId && it.title == request.title && it.transactionType == request.transactionType }))
                    .willReturn(category)

            val resultCategory = service.create(userId, request)
            assertThat(resultCategory).isEqualTo(category)
        }

        @Test
        fun `카테고리명이 중복되면 에러`() {
            given(repository.existsByUserIdAndTitle(userId, request.title)).willReturn(true)

            assertThrows<BadRequestException> {
                service.create(userId, request)
            }

        }
    }
}

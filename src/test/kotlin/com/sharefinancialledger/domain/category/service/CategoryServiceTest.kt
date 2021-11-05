package com.sharefinancialledger.domain.category.service

import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.controller.dto.UpdateCategoryRequest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.global.entity.type.TransactionType
import javassist.NotFoundException
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

    private val userId = 100

    @Nested
    inner class CreateCategory {

        @Test
        fun `카테고리를 생성한다`() {
            val request = CreateCategoryRequest("카테고리", TransactionType.INCOME)
            val category = Category(userId = userId, title = request.title, transactionType = request.transactionType)
            given(repository.save(argThat { it.userId == userId && it.title == request.title && it.transactionType == request.transactionType }))
                    .willReturn(category)

            val resultCategory = service.create(userId, request)
            assertThat(resultCategory).isEqualTo(category)
        }
    }

    @Nested
    inner class UpdateCategory {

        private val request = UpdateCategoryRequest("카테고리리리리")

        @Test
        fun `카테고리를 수정한다`() {
            val category = Category(200, userId, "카테고리다앙", TransactionType.EXPENDITURE)
            given(repository.findByIdAndUserId(category.id!!, userId)).willReturn(category)
            service.update(category.id!!, userId, request)
        }

        @Test
        fun `유저의 카테고리가 아니거나 없는 카테고리 ID이면 에러`() {
            assertThrows<NotFoundException> {
                service.update(userId, 123, request
                )
            }
        }
    }
}

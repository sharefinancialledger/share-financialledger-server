package com.sharefinancialledger.domain.transactionlog.service

import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.service.CategoryService
import com.sharefinancialledger.domain.transactionlog.controller.dto.CreateTransactionLogRequest
import com.sharefinancialledger.domain.transactionlog.repository.TransactionLogRepository
import com.sharefinancialledger.global.entity.type.TransactionType
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.argThat
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.time.LocalDate

internal class TransactionLogServiceTest {

    private val repository: TransactionLogRepository = mock(TransactionLogRepository::class.java)
    private val categoryService: CategoryService = mock(CategoryService::class.java)
    private val service = TransactionLogService(repository, categoryService)

    private val userId = 100
    private val today = LocalDate.now()

    @Test
    fun `입출력 이력을 저장한다`() {
        val request = CreateTransactionLogRequest(today, "떡볶이", 3000, 200)

        val category = Category(300, userId, "식사", TransactionType.EXPENDITURE)
        given(categoryService.findCategoryOrRaiseIfNotExist(request.categoryId, userId))
                .willReturn(category)

        service.create(userId, request)

        verify(repository).save(
                argThat { it.date == request.date && it.name == request.name && it.category.id == category.id }
        )
    }
}

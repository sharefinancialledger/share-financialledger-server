package com.sharefinancialledger.domain.transactionlog.service

import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.service.CategoryService
import com.sharefinancialledger.domain.subcategory.entity.SubCategory
import com.sharefinancialledger.domain.subcategory.service.SubCategoryService
import com.sharefinancialledger.domain.transactionlog.controller.dto.CreateTransactionLogRequest
import com.sharefinancialledger.domain.transactionlog.entity.TransactionLog
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
    private val subCategoryService: SubCategoryService = mock(SubCategoryService::class.java)
    private val service = TransactionLogService(repository, categoryService, subCategoryService)

    private val userId = 100
    private val today = LocalDate.now()

    @Test
    fun `입출력 이력을 저장한다`() {
        val request = CreateTransactionLogRequest(today, "떡볶이", 3000, 200)

        val category = Category(200, userId, "식사", TransactionType.EXPENDITURE)
        given(categoryService.findOwn(request.categoryId, userId)).willReturn(category)
        given(repository.save(argThat { it.date == request.date && it.name == request.name && it.category.id == category.id }))
            .willReturn(TransactionLog(userId = userId, date = today, name = "식사", amount = 3000))

        service.create(userId, request)
    }

    @Test
    fun `서브카테고리가 있는 입출력 이력을 저장한다`() {
        val request = CreateTransactionLogRequest(today, "뿌링클", 4000, 200, 201)

        val category = Category(200, userId, "식사", TransactionType.EXPENDITURE)
        given(categoryService.findOwn(request.categoryId, userId)).willReturn(category)

        val subCategory = SubCategory(201, userId, "bhc", 200)
        given(subCategoryService.findOwn(request.subCategoryId!!, userId)).willReturn(subCategory)

        given(repository.save(argThat { it.date == request.date && it.name == request.name && it.category.id == category.id && it.subcategory?.id == subCategory.id }))
            .willReturn(TransactionLog(userId = userId, date = today, name = "식사", amount = 3000))
    }
}

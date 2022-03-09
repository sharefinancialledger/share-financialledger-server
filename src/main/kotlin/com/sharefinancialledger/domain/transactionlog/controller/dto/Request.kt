package com.sharefinancialledger.domain.transactionlog.controller.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.AssertFalse
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Future

data class CreateTransactionLogRequest(
    val date: LocalDate,
    val name: String,
    val amount: Int,
    val categoryId: Int,
    val subCategoryId: Int? = null
)

data class FindTransactionLogRequest(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val startDate: LocalDate,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val endDate: LocalDate
) {

    @AssertTrue(message = "시작일은 종료일보다 빠를 수 없습니다.")
    fun isEndDateIsAfterOrEqualThenStartDate() = endDate >= startDate // 이름이 is로 시작해야 동작함...
}
package com.sharefinancialledger.domain.transactionlog.controller.dto

import com.sharefinancialledger.domain.transactionlog.entity.TransactionLog
import com.sharefinancialledger.global.entity.type.TransactionType
import java.time.LocalDate

data class FindTransactionLogResponse(
    val transactionLogs: List<Data>
) {
    data class Data(
        val id: Int,
        val name: String,
        val date: LocalDate,
        val amount: Int,
        val category: Category,
        val subCategory: SubCategory? = null
    ) {
        data class Category(
            val id: Int,
            val title: String,
            val transactionType: TransactionType
        )

        data class SubCategory(
            val id: Int,
            val title: String
        )

        companion object {
            fun mapFrom(transactionLogs: Collection<TransactionLog>): List<Data> {
                return transactionLogs.map { from(it) }
            }

            fun from(transactionLog: TransactionLog): Data {
                return with(transactionLog) {
                    Data(
                        id = id!!,
                        name = name,
                        date = date,
                        amount = amount,
                        category = Category(category.id!!, category.title, category.transactionType),
                        subCategory = subCategory?.let { SubCategory(it.id!!, it.title) }
                    )
                }
            }
        }
    }
}
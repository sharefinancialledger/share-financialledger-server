package com.sharefinancialledger.domain.transactionlog.repository

import com.sharefinancialledger.domain.transactionlog.entity.TransactionLog
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface TransactionLogRepository : JpaRepository<TransactionLog, Int> {

    @EntityGraph(attributePaths = ["category", "subCategory"], type = EntityGraphType.FETCH)
    fun findAllByUserIdAndDateBetweenOrderByDateDesc(userId: Int, startDate: LocalDate, endDate: LocalDate): List<TransactionLog>
}
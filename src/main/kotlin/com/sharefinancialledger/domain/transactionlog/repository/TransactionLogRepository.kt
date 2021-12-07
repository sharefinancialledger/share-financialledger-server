package com.sharefinancialledger.domain.transactionlog.repository

import com.sharefinancialledger.domain.transactionlog.entity.TransactionLog
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionLogRepository : JpaRepository<TransactionLog, Int>
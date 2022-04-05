package com.sharefinancialledger.domain.transactionlog.controller.dto

import com.sharefinancialledger.domain.transactionlog.service.TransactionLogService
import com.sharefinancialledger.domain.user.entity.User
import com.sharefinancialledger.global.controller.dto.CreateResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.validation.Valid

@RestController
class TransactionLogController(
        private val service: TransactionLogService
) {

    @PostMapping("/v1/transaction-logs")
    fun create(
        @AuthenticationPrincipal user: User,
        @RequestBody request: CreateTransactionLogRequest
    ): ResponseEntity<CreateResponse> {
        val transactionLog = service.create(user.id!!, request)
        return ResponseEntity.created(URI.create("")).body(CreateResponse(transactionLog.id!!))
    }

    @GetMapping("/v1/transaction-logs")
    fun findAll(
        @AuthenticationPrincipal user: User,
        @Valid request: FindTransactionLogRequest
    ): FindTransactionLogResponse {
        return FindTransactionLogResponse(FindTransactionLogResponse.Data.mapFrom(service.findAll(user.id!!, request)))
    }
}
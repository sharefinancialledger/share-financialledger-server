package com.sharefinancialledger.domain.transactionlog.integration

import com.sharefinancialledger.base.IntegrationTest
import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.domain.transactionlog.controller.dto.CreateTransactionLogRequest
import com.sharefinancialledger.global.entity.type.TransactionType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import reactor.core.publisher.Mono
import java.time.LocalDate

class TransactionLogIntegrationTest : IntegrationTest() {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    private val today = LocalDate.now()

    @Test
    fun `입출력 이력 생성`() {
        val authResponse = authenticate()

        client
                .post()
                .uri("/api/v1/categories")
                .header("Authorization", "Bearer ${authResponse.token}")
                .body(Mono.just(CreateCategoryRequest("월급", TransactionType.INCOME)), CreateCategoryRequest::class.java)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated
                .expectBody().isEmpty


        val category = categoryRepository.findAll().first { it.title == "월급" }

        val request = CreateTransactionLogRequest(
                date = today,
                name = "월급",
                amount = 10000000,
                categoryId = category.id!!,
                subCategoryId = null
        )

        client.post()
                .uri("/v1/transaction-logs")
                .header("Authorization", "Bearer ${authResponse.token}")
                .body(Mono.just(request), CreateTransactionLogRequest::class.java)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated
                .expectBody().isEmpty
    }
}
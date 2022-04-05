package com.sharefinancialledger.domain.transactionlog.integration

import com.sharefinancialledger.base.IntegrationTest
import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.domain.transactionlog.controller.dto.CreateTransactionLogRequest
import com.sharefinancialledger.domain.transactionlog.controller.dto.FindTransactionLogRequest
import com.sharefinancialledger.global.entity.type.TransactionType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
                .expectBody()
            .jsonPath("id").exists()

        client.get()
            .uri {
                it.path("/v1/transaction-logs")
                    .queryParam("startDate", LocalDate.now().minusDays(5).format(DateTimeFormatter.ISO_DATE))
                    .queryParam("endDate", LocalDate.now().format(DateTimeFormatter.ISO_DATE))
                    .build()
            }
            .header("Authorization", "Bearer ${authResponse.token}")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("transactionLogs").isNotEmpty
            .jsonPath("transactionLogs[*].id").exists()
            .jsonPath("transactionLogs[*].name").exists()
            .jsonPath("transactionLogs[*].date").exists()
            .jsonPath("transactionLogs[*].amount").exists()
            .jsonPath("transactionLogs[*].category").exists()
            .jsonPath("transactionLogs[*].category.id").exists()
            .jsonPath("transactionLogs[*].category.title").exists()
            .jsonPath("transactionLogs[*].category.transactionType").exists()
            .jsonPath("transactionLogs[*].subCategory").exists()
    }
}
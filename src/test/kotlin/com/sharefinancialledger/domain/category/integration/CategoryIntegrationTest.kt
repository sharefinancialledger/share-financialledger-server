package com.sharefinancialledger.domain.category.integration

import com.sharefinancialledger.base.IntegrationTest
import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.controller.dto.UpdateCategoryRequest
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.global.entity.type.TransactionType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import reactor.core.publisher.Mono


class CategoryIntegrationTest : IntegrationTest() {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Test
    fun `카테고리 생성`() {
        val authResponse = authenticate()

        client
                .post()
                .uri("/api/v1/categories")
                .header("Authorization", "Bearer ${authResponse.token}")
                .body(Mono.just(CreateCategoryRequest("카테고리", TransactionType.INCOME)), CreateCategoryRequest::class.java)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated
                .expectBody().isEmpty

    }

    // TODO WebMvcTest로 이전
    @Test
    fun `카테고리명 없으면 에러`() {
        val authResponse = authenticate()

        client
                .post()
                .uri("/api/v1/categories")
                .header("Authorization", "Bearer ${authResponse.token}")
                .body(Mono.just(CreateCategoryRequest("", TransactionType.INCOME)), CreateCategoryRequest::class.java)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
    }


    // TODO WebMvcTest로 이전
    @Test
    fun `카테고리명이 30글자를 초과하면 에러`() {
        val authResponse = authenticate()

        client
                .post()
                .uri("/api/v1/categories")
                .header("Authorization", "Bearer ${authResponse.token}")
                .body(Mono.just(CreateCategoryRequest("하".padStart(31, '하'), TransactionType.INCOME)), CreateCategoryRequest::class.java)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
    }

    @Test
    fun `카테고리 수정`() {
        val authResponse = authenticate()

        client
                .post()
                .uri("/api/v1/categories")
                .header("Authorization", "Bearer ${authResponse.token}")
                .body(Mono.just(CreateCategoryRequest("카테고리만들었다", TransactionType.INCOME)), CreateCategoryRequest::class.java)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()

        val categories = categoryRepository.findAll()
        categories.sortByDescending { it.id }

        client
                .patch()
                .uri("/api/v1/categories/${categories.first().id!!}")
                .header("Authorization", "Bearer ${authResponse.token}")
                .body(Mono.just(UpdateCategoryRequest("카테고리수정했다")), UpdateCategoryRequest::class.java)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
    }

}
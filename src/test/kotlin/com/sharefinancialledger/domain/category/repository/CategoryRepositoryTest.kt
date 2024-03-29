package com.sharefinancialledger.domain.category.repository

import com.sharefinancialledger.base.RepositoryTest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.global.entity.type.TransactionType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.InvalidDataAccessApiUsageException


class CategoryRepositoryTest : RepositoryTest() {

    @Autowired
    lateinit var repository: CategoryRepository

    @Test
    fun `수입 타입의 카테고리를 생성한다`() {
        repository.save(Category(userId = 100, title = "카테고리", transactionType = TransactionType.INCOME))

        val categories = repository.findAll()

        assertThat(categories).hasSize(1)
        assertThat(categories.first())
                .returns("카테고리") { it.title }
                .returns(TransactionType.INCOME) { it.transactionType }
    }

    @Test
    fun `카테고리명이 30글자 초과된 경우 에러`() {
        assertThrows<InvalidDataAccessApiUsageException> {
            repository.save(Category(userId = 100, title = "하".padStart(32, '하'), transactionType = TransactionType.INCOME))
        }
    }

}
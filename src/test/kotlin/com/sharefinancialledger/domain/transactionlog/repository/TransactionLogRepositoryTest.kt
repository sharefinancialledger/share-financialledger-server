package com.sharefinancialledger.domain.transactionlog.repository

import com.sharefinancialledger.base.RepositoryTest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.domain.transactionlog.entity.TransactionLog
import com.sharefinancialledger.global.entity.type.TransactionType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

class TransactionLogRepositoryTest : RepositoryTest() {

    @Autowired
    lateinit var repository: TransactionLogRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    lateinit var mealCategory: Category

    private val today = LocalDate.now()

    @BeforeEach
    fun beforeEach() {
        mealCategory = categoryRepository.save(
                Category(userId = 100, title = "식사", transactionType = TransactionType.EXPENDITURE)
        )
    }

    @Test
    fun `입출력 이력을 저장할 수 있다`() {
        val transactionLog = TransactionLog(userId = 100, date = today, name = "카페", amount = 1000)
                .apply { category = mealCategory }

        repository.save(transactionLog)

        assertThat(repository.findAll().first())
                .returns("카페") { it.name }
                .returns(mealCategory.id) { it.category.id }
    }

}
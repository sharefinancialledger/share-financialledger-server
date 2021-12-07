package com.sharefinancialledger.domain.transactionlog.repository

import com.sharefinancialledger.base.RepositoryTest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.category.repository.CategoryRepository
import com.sharefinancialledger.domain.subcategory.entity.SubCategory
import com.sharefinancialledger.domain.subcategory.repository.SubCategoryRepository
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

    @Autowired
    lateinit var subCategoryRepository: SubCategoryRepository

    private val today = LocalDate.now()

    @Test
    fun `입출력 이력을 저장할 수 있다`() {
        val category =
            categoryRepository.save(Category(userId = 100, title = "식사", transactionType = TransactionType.EXPENDITURE))
        val subCategory =
            subCategoryRepository.save(SubCategory(userId = 100, title = "김밥천국", categoryId = category.id!!))

        val transactionLog = TransactionLog(userId = 100, date = today, name = "카페", amount = 1000)
            .apply {
                this.category = category
                this.subcategory = subCategory
            }

        repository.save(transactionLog)

        assertThat(repository.findAll().first())
            .returns("카페") { it.name }
            .returns(category.id) { it.category.id }
            .returns(subCategory.id) { it.subcategory?.id }
    }

}
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

    lateinit var defaultCategory: Category
    lateinit var defaultSubCategory: SubCategory

    @BeforeEach
    fun beforeEach() {
        defaultCategory = categoryRepository.save(Category(userId = 100, title = "식사", transactionType = TransactionType.EXPENDITURE))
        defaultSubCategory = subCategoryRepository.save(SubCategory(userId = 100, title = "김밥천국", categoryId = defaultCategory.id!!))
    }

//    @Test
//    fun `입출력 이력을 저장할 수 있다`() {
//        val category =
//            categoryRepository.save(Category(userId = 100, title = "식사", transactionType = TransactionType.EXPENDITURE))
//        val subCategory =
//            subCategoryRepository.save(SubCategory(userId = 100, title = "김밥천국", categoryId = category.id!!))
//
//        val transactionLog = TransactionLog(userId = 100, date = today, name = "카페", amount = 1000)
//            .apply {
//                this.category = category
//                this.subcategory = subCategory
//            }
//
//        repository.save(transactionLog)
//
//        assertThat(repository.findAll().first())
//            .returns("카페") { it.name }
//            .returns(category.id) { it.category.id }
//            .returns(subCategory.id) { it.subcategory?.id }
//    }

    @Test
    fun `입출력 이력을 조회한다`() {
        val userId = 200
        repository.saveAll(
            listOf(
                createTransactionLog(userId, LocalDate.of(2021, 1, 1)),
                createTransactionLog(userId, LocalDate.of(2021, 3, 2)),
                createTransactionLog(userId, LocalDate.of(2022, 2, 27)),
                createTransactionLog(userId, LocalDate.of(2022, 3, 4)),
                createTransactionLog(userId, LocalDate.of(2022, 3, 1)),
            )
        )

        // left outer join
        val result = repository.findAllByUserIdAndDateBetweenOrderByDateDesc(
            userId,
            startDate = LocalDate.of(2022, 3, 1),
            endDate = LocalDate.of(2022, 3, 5)
        )
        assertThat(result).hasSize(2)
        assertThat(result.map { it.date }).containsAll(listOf(LocalDate.of(2022, 3, 1), LocalDate.of(2022, 3, 4)))
    }

    private fun createTransactionLog(userId: Int, date: LocalDate): TransactionLog {
        return TransactionLog(userId= userId, date = date, name = "$date 씀", amount = 1000)
            .apply {
                this.category = defaultCategory
                this.subCategory = defaultSubCategory
            }
    }
}
package com.sharefinancialledger.domain.subcategory.repository

import com.sharefinancialledger.base.RepositoryTest
import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.subcategory.entity.SubCategory
import com.sharefinancialledger.global.entity.type.TransactionType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException

class SubCategoryRepositoryTest:RepositoryTest() {
    @Autowired
    lateinit var repository: SubCategoryRepository

    @Test
    fun `서브카테고리를 생성한다`() {
        repository.save(SubCategory(userId = 100, title = "sub category", categoryId = 10))

        val categories = repository.findAll()

        Assertions.assertThat(categories).hasSize(1)
        Assertions.assertThat(categories.first())
                .returns("sub category") { it.title }
    }

    @Test
    fun `do not create subcategory when title is bigger then 30 -- should throw DataIntegrityViolationException`(){
        assertThrows<DataIntegrityViolationException> {
            repository.save(SubCategory(userId = 100, title="1234567890123456789012345678901", categoryId = 10))
        }
    }
}
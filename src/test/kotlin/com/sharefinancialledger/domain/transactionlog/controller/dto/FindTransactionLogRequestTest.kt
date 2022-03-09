package com.sharefinancialledger.domain.transactionlog.controller.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import java.time.LocalDate
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory


class FindTransactionLogRequestTest {

    lateinit var validator: Validator

    @BeforeEach
    fun beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().validator
    }

    @Test
    fun `startDate가 endDate보다 크면 에러`() {
        validator.validate(FindTransactionLogRequest(LocalDate.of(2022, 3, 9), LocalDate.of(2022, 3, 5)))
            .let {
                assertThat(it).hasSize(1)
            }
    }
}
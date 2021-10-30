package com.sharefinancialledger.user.repository

import com.sharefinancialledger.user.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    lateinit var repository: UserRepository

    @Test
    fun `이메일로 유저를 조회할 수 있다`() {
        repository.save(User(email = "lowell@naver.com", password = "password", name = "로웰"))

        val result = repository.findFirstByEmail("lowell@naver.com")
        assertThat(result).isPresent
    }
}
package com.sharefinancialledger.user.service

import com.sharefinancialledger.exception.BadRequestException
import com.sharefinancialledger.user.controller.dto.CreateUserRequest
import com.sharefinancialledger.user.entity.User
import com.sharefinancialledger.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.argThat
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*


class UserServiceTest {

    private val repository: UserRepository = Mockito.mock(UserRepository::class.java)
    private val service = UserService(repository)

    @Nested
    inner class CreateUser {
        private val request = CreateUserRequest("lowell@gmail.com", "password", "lowell")
        private val user = User(1, request.email, request.password, request.name)

        @Test
        fun `유저를 생성한다`() {
            given(
                    repository.save(
                            argThat { it.email == request.email && it.password == request.password && it.name == request.name }
                    )
            ).willReturn(user)

            val id = service.create(request)

            assertThat(id).isEqualTo(1)
        }

        @Test
        fun `중복된 이메일이면 유저 생성을 실패한다`() {
            given(repository.findFirstByEmail(request.email)).willReturn(Optional.of(user))

            assertThrows<BadRequestException> {
                service.create(request)
            }
        }
    }


}

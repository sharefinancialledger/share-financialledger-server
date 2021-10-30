package com.sharefinancialledger.base

import com.fasterxml.jackson.databind.ObjectMapper
import com.sharefinancialledger.domain.auth.dto.AuthenticationRequest
import com.sharefinancialledger.domain.auth.dto.AuthenticationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class IntegrationTest {

    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var objectMapper: ObjectMapper

    protected fun authenticate(): AuthenticationResponse {
        return client
                .post()
                .uri("/api/v1/authentication")
                .body(Mono.just(AuthenticationRequest("email@gmail.com", "password")), AuthenticationRequest::class.java)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody().returnResult().responseBody
                .let { objectMapper.readValue(it, AuthenticationResponse::class.java) }
    }
}